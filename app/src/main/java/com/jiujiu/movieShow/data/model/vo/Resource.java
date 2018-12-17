package com.jiujiu.movieShow.data.model.vo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable String message, @Nullable T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, null, data);
    }

    public static <T> Resource<T> error(String message , @Nullable T data) {
        return new Resource<>(Status.ERROR, message, data);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource<>(Status.LOADING, null, data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
