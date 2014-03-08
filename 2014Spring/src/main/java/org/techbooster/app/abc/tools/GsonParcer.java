package org.techbooster.app.abc.tools;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * original is here https://github.com/square/flow/blob/master/flow-sample/src/main/java/com/example/flow/GsonParcer.java
 */
public class GsonParcer {
    private static final Gson gson = new Gson();

    private GsonParcer() {
    }

    public static <T> Parcelable wrap(T instance) {
        try {
            String json = encode(instance);
            Log.d("moge", json);
            return new Wrapper(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T unwrap(Parcelable parcelable) {
        Wrapper wrapper = (Wrapper) parcelable;
        try {
            return decode(wrapper.json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, U> T unwrap(Parcelable parcelable, TypeToken<U> token) {
        Wrapper wrapper = (Wrapper) parcelable;
        try {
            return decode(wrapper.json, token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> String encode(T instance) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);

        try {
            Class<?> type = instance.getClass();

            writer.beginObject();
            writer.name(type.getName());
            gson.toJson(instance, type, writer);
            writer.endObject();

            return stringWriter.toString();
        } finally {
            writer.close();
        }
    }

    private static <T> T decode(String json) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(json));
        try {
            reader.beginObject();
            Class<?> type = Class.forName(reader.nextName());
            return gson.fromJson(reader, type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            reader.close();
        }
    }

    private static <T, U> T decode(String json, TypeToken<U> token) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(json));
        try {
            reader.beginObject();
            reader.nextName();
            return gson.fromJson(reader, token.getType());
        } finally {
            reader.close();
        }
    }

    private static class Wrapper implements Parcelable {
        final String json;

        Wrapper(String json) {
            this.json = json;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(json);
        }

        public static final Creator<Wrapper> CREATOR = new Creator<Wrapper>() {
            @Override
            public Wrapper createFromParcel(Parcel in) {
                String json = in.readString();
                return new Wrapper(json);
            }

            @Override
            public Wrapper[] newArray(int size) {
                return new Wrapper[size];
            }
        };
    }
}
