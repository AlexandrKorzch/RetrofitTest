package com.korzh.user.retrofittest.util;

/**
 * Created by user on 10.08.17.
 */

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;

public class MyLogInterceptor implements Interceptor {

    String TAG = "Retrofit";

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private final Logger logger;
    private volatile Level level;

    public MyLogInterceptor() {
        this(Logger.DEFAULT);
    }

    private MyLogInterceptor(Logger logger) {
        this.level = Level.NONE;
        this.logger = logger;
    }

    public void setLevel(Level level) {
        if(level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        } else {
            this.level = level;
            return;
        }
    }

    public Level getLevel() {
        return this.level;
    }

    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Level level = this.level;
        if(level == Level.NONE) {
            return chain.proceed(request);
        } else {
            boolean logBody = level == Level.BODY;
            boolean logHeaders = logBody || level == Level.HEADERS;
            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;
            Connection connection = chain.connection();
            Protocol protocol = connection != null?connection.protocol():Protocol.HTTP_1_1;
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            if(!logHeaders && hasRequestBody) {
                requestStartMessage = requestStartMessage + " (" + requestBody.contentLength() + "-byte body)";
            }

            this.logger.log("");
            this.logger.log("...........................................................................................................");
            this.logger.log("");
            this.logger.log(requestStartMessage);
//            LogToFileUtil.saveLogToFile(requestStartMessage);
            if(logHeaders) {

                if(logBody && hasRequestBody) {
                    if(!this.bodyEncoded(request.headers())) {
                        Buffer var29 = new Buffer();
                        requestBody.writeTo(var29);
                        Charset var30 = UTF8;
                        MediaType var32 = requestBody.contentType();
                        if(var32 != null) {
                            var30 = var32.charset(UTF8);
                        }
                        if(isPlaintext(var29)) {
                            this.logger.log(var29.readString(var30));
//                            LogToFileUtil.saveLogToFile(var29.readString(var30));
                        }
                    }
                }
            }

            long var28 = System.nanoTime();

            Response var31;
            try {
                var31 = chain.proceed(request);
            } catch (Exception var27) {
                this.logger.log("");
                this.logger.log("<-- HTTP FAILED: " + var27);
//                LogToFileUtil.saveLogToFile("<-- HTTP FAILED: " + var27);
                throw var27;
            }

            long var33 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - var28);
            ResponseBody responseBody = var31.body();
            long contentLength = responseBody.contentLength();
            String bodySize = contentLength != -1L?contentLength + "-byte":"unknown-length";
            this.logger.log("");
            this.logger.log("<-- " + var31.code() + ' ' + var31.message() + ' ' + var31.request().url() + " (" + var33 + "ms" + (!logHeaders?", " + bodySize + " body":"") + ')');
//            LogToFileUtil.saveLogToFile("<-- " + var31.code() + ' ' + var31.message() + ' ' + var31.request().url() + " (" + var33 + "ms" + (!logHeaders?", " + bodySize + " body":"") + ')');

            if(logHeaders) {
                if(logBody && HttpHeaders.hasBody(var31)) {
                    if(this.bodyEncoded(var31.headers())) {
                    } else {
                        BufferedSource var34 = responseBody.source();
                        var34.request(9223372036854775807L);
                        Buffer var35 = var34.buffer();
                        Charset charset = UTF8;
                        MediaType contentType = responseBody.contentType();
                        if(contentType != null) {
                            try {
                                charset = contentType.charset(UTF8);
                            } catch (UnsupportedCharsetException var26) {
                                return var31;
                            }
                        }

                        if(!isPlaintext(var35)) {
                            this.logger.log("");
                            return var31;
                        }

                        if(contentLength != 0L) {
                            this.logger.log("");
                            this.logger.log(var35.clone().readString(charset));
//                            LogToFileUtil.saveLogToFile(var35.clone().readString(charset));
                        }

                    }
                }
            }

            return var31;
        }
    }

    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer e = new Buffer();
            long byteCount = buffer.size() < 64L?buffer.size():64L;
            buffer.copyTo(e, 0L, byteCount);

            for(int i = 0; i < 16 && !e.exhausted(); ++i) {
                int codePoint = e.readUtf8CodePoint();
                if(Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }

            return true;
        } catch (EOFException var6) {
            return false;
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    public interface Logger {

        String TAG = "MyRetrofitLog";
        Logger DEFAULT = message -> {
            Platform.get().log(4, message, null);
            Log.d(TAG, message);
        };

        void log(String var1);
    }

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY;

        Level() {
        }
    }
}