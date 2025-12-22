package com.courseproject.mlkuniversity;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPRequests {
    static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MILLISECONDS)
            .build();


    public JSONObject fqwPostRequest(Context context)
    {
        SharedPreferences settings = context.getSharedPreferences("Account", MODE_PRIVATE);

        RequestBody requestBody = new FormBody.Builder()
                .add("teacher_name", settings.getString("name", "err"))
                .build();
        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + context.getString(R.string.fqw_request))
                .post(requestBody)
                .build();
        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println(s);
            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                            "Не удалось подключиться к серверу",
                            Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }

    // POST запрос для входа в аккаунт.
    public JSONObject logInPostRequest(Context context, String email, String password)
    {
        RequestBody requestBody = new FormBody.Builder()
                .add("email",email)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + context.getString(R.string.login_request))
                .post(requestBody)
                .build();
        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println("LOGIN\n" + s);
            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                    "Не удалось подключиться к серверу",
                    Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }


    // отправляет POST запрос с регистрационными данными на сервер для создания нового пользователя.
    public JSONObject SignUpPostRequest(Context context, String name, String email, String password, String ID, String SNILS)
    {
        RequestBody requestBody = new FormBody.Builder()
                .add("name",name)
                .add("email",email)
                .add("snils",SNILS)
                .add("passport",ID)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + context.getString(R.string.signup_request))
                .post(requestBody)
                .build();

        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println(s);

            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                    "Не удалось подключиться к серверу",
                    Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }


    public JSONObject sendImagePostRequest(Context context, Uri uri)
    {
        SharedPreferences settings = context.getSharedPreferences("Account", MODE_PRIVATE);

        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", settings.getString("email", "err"))
                .addFormDataPart("image",
                        settings.getString("email", "err") + ".png",
                        RequestBody.create(byteArray,MediaType.parse("image/*png")))
                .build();
        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + context.getString(R.string.upload_profile_picture_request))
                .post(requestBody)
                .build();
        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println(s);

            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                            "Не удалось подключиться к серверу",
                            Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }

    // Отправляет POST запрос с параметром имени пользователя на сервер; возвращает массив JSON объектов.
    public JSONObject financePostRequest(Context context)
    {
        SharedPreferences settings = context.getSharedPreferences("Account", MODE_PRIVATE);

        RequestBody requestBody = new FormBody.Builder()
                .add("name", settings.getString("name", "err"))
                .build();
        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + context.getString(R.string.finance_request))
                .post(requestBody)
                .build();
        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println(s);
            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                    "Не удалось подключиться к серверу",
                    Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }


    // отправляет GET-запрос на сервер и возвращает массив JSON объектов.
    public JSONObject JSONGetRequest (Context context, String link_ending)
    {
        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + link_ending)
                .build();
        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println(s);
            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                            "Не удалось подключиться к серверу",
                            Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }


    // отправляет GET-запрос на сервер и возвращает массив JSON объектов.
    public JSONObject ScheduleGetRequest(Context context, String group, String teacher, String dateStart, String dateEnd)
    {
        Request request = new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme("http")
                        // TODO: Это пиздец какой костыль.
                        .host(context.getString(R.string.base_url).substring(7, context.getString(R.string.base_url).length() - 1))
                        .addPathSegment("/" + context.getString(R.string.schedule_request))
                        //
                        .addQueryParameter("group_name", group)
                        .addQueryParameter("full_name", teacher)
                        .addQueryParameter("start_date", dateStart)
                        .addQueryParameter("end_date", dateEnd)
                        .build())
                .build();
        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println(s);
            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                    "Не удалось подключиться к серверу",
                    Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }


    public JSONObject changePasswordPostRequest(Context context, String email, String password)
    {
        RequestBody requestBody = new FormBody.Builder()
                .add("email",email)
                .add("new_password",password)
                .build();
        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + context.getString(R.string.password_change_request))
                .post(requestBody)
                .build();
        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println(s);
            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                    "Не удалось подключиться к серверу",
                    Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }


    public JSONObject recoverPasswordPostRequest(Context context, String email, String ID, String SNILS, String password)
    {
        RequestBody requestBody = new FormBody.Builder()
                .add("email",email)
                .add("snils",SNILS)
                .add("passport",ID)
                .add("new_password",password)
                .build();
        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + context.getString(R.string.password_recover_request))
                .post(requestBody)
                .build();
        CallbackFuture future = new CallbackFuture();

        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String s = response.body().string();
            System.out.println(s);
            return new JSONObject(s);
        }
        catch (InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                            "Не удалось подключиться к серверу",
                            Toast.LENGTH_SHORT)
                    .show();
            return new JSONObject();
        }
    }


    //
    public Bitmap BitmapGetRequest(Context context, String link_ending)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url) + link_ending)
                .build();
        System.out.println(context.getString(R.string.base_url) + link_ending);

        CallbackFuture future = new CallbackFuture();
        httpClient.newCall(request).enqueue(future);
        try
        {
            Response response = future.get();
            if (!response.isSuccessful())
            {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            return BitmapFactory.decodeStream(response.body().byteStream());
        }
        catch (InterruptedException | IOException e)
        {
            System.out.println(e);
            return null;
        }
        catch (ExecutionException e)
        {
            Toast.makeText(context,
                            "Не удалось подключиться к серверу",
                            Toast.LENGTH_SHORT)
                    .show();
            return null;
        }
    }


    class CallbackFuture extends CompletableFuture<Response> implements Callback {
        public void onResponse(Call call, Response response) {
            super.complete(response);
        }
        public void onFailure(Call call, IOException e){
            super.completeExceptionally(e);
        }
    }
}
