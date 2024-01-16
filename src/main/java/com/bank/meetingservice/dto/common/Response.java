package com.bank.meetingservice.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.bank.meetingservice.enums.ResponseStatus;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Response<T> {
    private ResponseStatus status;
    private String message;
    private T data;
    private String errorCode;
    private String errorDetails;
    private String uriPath;
    private String trace;

    public boolean success() {
        return Objects.nonNull(this.status) && this.status == ResponseStatus.SUCCESS;
    }

    public Response(ResponseStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean hasData() {
        return Objects.nonNull(this.data);
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder();
    }

    public ResponseStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorDetails() {
        return this.errorDetails;
    }

    public String getUriPath() {
        return this.uriPath;
    }

    public String getTrace() {
        return this.trace;
    }

    public void setStatus(final ResponseStatus status) {
        this.status = status;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorDetails(final String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public void setUriPath(final String uriPath) {
        this.uriPath = uriPath;
    }

    public void setTrace(final String trace) {
        this.trace = trace;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Response)) {
            return false;
        } else {
            Response<?> other = (Response)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label95: {
                    Object this$status = this.getStatus();
                    Object other$status = other.getStatus();
                    if (this$status == null) {
                        if (other$status == null) {
                            break label95;
                        }
                    } else if (this$status.equals(other$status)) {
                        break label95;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                label74: {
                    Object this$errorCode = this.getErrorCode();
                    Object other$errorCode = other.getErrorCode();
                    if (this$errorCode == null) {
                        if (other$errorCode == null) {
                            break label74;
                        }
                    } else if (this$errorCode.equals(other$errorCode)) {
                        break label74;
                    }

                    return false;
                }

                label67: {
                    Object this$errorDetails = this.getErrorDetails();
                    Object other$errorDetails = other.getErrorDetails();
                    if (this$errorDetails == null) {
                        if (other$errorDetails == null) {
                            break label67;
                        }
                    } else if (this$errorDetails.equals(other$errorDetails)) {
                        break label67;
                    }

                    return false;
                }

                Object this$uriPath = this.getUriPath();
                Object other$uriPath = other.getUriPath();
                if (this$uriPath == null) {
                    if (other$uriPath != null) {
                        return false;
                    }
                } else if (!this$uriPath.equals(other$uriPath)) {
                    return false;
                }

                Object this$trace = this.getTrace();
                Object other$trace = other.getTrace();
                if (this$trace == null) {
                    if (other$trace != null) {
                        return false;
                    }
                } else if (!this$trace.equals(other$trace)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Response;
    }

    public String toString() {
        ResponseStatus var10000 = this.getStatus();
        return "Response(status=" + var10000 + ", message=" + this.getMessage() + ", data=" + this.getData() + ", errorCode=" + this.getErrorCode() + ", errorDetails=" + this.getErrorDetails() + ", uriPath=" + this.getUriPath() + ", trace=" + this.getTrace() + ")";
    }

    public Response() {
    }

    public Response(final ResponseStatus status, final String message, final T data, final String errorCode, final String errorDetails, final String uriPath, final String trace) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.uriPath = uriPath;
        this.trace = trace;
    }

    public static class ResponseBuilder<T> {
        private ResponseStatus status;
        private String message;
        private T data;
        private String errorCode;
        private String errorDetails;
        private String uriPath;
        private String trace;

        ResponseBuilder() {
        }

        public ResponseBuilder<T> status(final ResponseStatus status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder<T> data(final T data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder<T> errorCode(final String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ResponseBuilder<T> errorDetails(final String errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        public ResponseBuilder<T> uriPath(final String uriPath) {
            this.uriPath = uriPath;
            return this;
        }

        public ResponseBuilder<T> trace(final String trace) {
            this.trace = trace;
            return this;
        }

        public Response<T> build() {
            return new Response(this.status, this.message, this.data, this.errorCode, this.errorDetails, this.uriPath, this.trace);
        }

        public String toString() {
            return "Response.ResponseBuilder(status=" + this.status + ", message=" + this.message + ", data=" + this.data + ", errorCode=" + this.errorCode + ", errorDetails=" + this.errorDetails + ", uriPath=" + this.uriPath + ", trace=" + this.trace + ")";
        }
    }
}
