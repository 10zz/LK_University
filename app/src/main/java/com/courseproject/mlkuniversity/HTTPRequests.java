package com.courseproject.mlkuniversity;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPRequests {
    static OkHttpClient httpClient = new OkHttpClient();


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

            // TODO: clean.
            String s = response.body().string();
            System.out.println("LOGIN\n" + s);
            return new JSONObject(s);
        }
        catch (ExecutionException | InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
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

            // TODO: clean.
            String s = response.body().string();
            System.out.println(s);

            return new JSONObject(s);
        }
        catch (ExecutionException | InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
    }


    // Отправляет POST запрос с параметром имени пользователя на сервер; возвращает массив JSON объектов.
    public JSONObject[] financePostRequest(Context context, String name)
    {
        // TODO
        /*RequestBody requestBody = new FormBody.Builder()
                .add("name", context.getSharedPreferences("Account", MODE_PRIVATE).getString("name", "null"))
                .build();*/
        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
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

            // TODO: clean.
            String s = response.body().string();
            System.out.println(s);
            JSONArray responseArr = new JSONArray(s);

            JSONObject[] objArray = new JSONObject[responseArr.length()];
            for (int i = 0; i < responseArr.length(); i++)
            {
                objArray[i] = responseArr.optJSONObject(i);
            }
            return objArray;
        }
        catch (ExecutionException | InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
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

            // TODO: clean.
            String s = response.body().string();
            System.out.println(s);
            return new JSONObject(s);
        }
        catch (ExecutionException | InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
    }


    // отправляет GET-запрос на сервер и возвращает массив JSON объектов.
    public JSONObject[] ScheduleGetRequest(Context context, String group, String teacher, String dateStart, String dateEnd)
    {


        Request request = new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme("http")
                        // TODO: Это пиздец какой костыль.
                        .host(context.getString(R.string.base_url).substring(7, context.getString(R.string.base_url).length() - 1))
                        .addPathSegment("/" + context.getString(R.string.schedule_request))
                        //
                        .addQueryParameter("group", group)
                        .addQueryParameter("teacher", teacher)
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

            // TODO: clean.
            String s = response.body().string();
            System.out.println(s);
            JSONArray responseArr = new JSONArray(s);

            JSONObject[] objArray = new JSONObject[responseArr.length()];
            for (int i = 0; i < responseArr.length(); i++)
            {
                objArray[i] = responseArr.optJSONObject(i);
            }
            return objArray;
        }
        catch (ExecutionException | InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
    }


    public JSONObject changePasswordPostRequest(Context context, String email, String password)
    {
        RequestBody requestBody = new FormBody.Builder()
                .add("email",email)
                .add("password",password)
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

            // TODO: clean.
            String s = response.body().string();
            System.out.println(s);
            return new JSONObject(s);
        }
        catch (ExecutionException | InterruptedException | IOException | JSONException e)
        {
            throw new RuntimeException(e);
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
        catch (ExecutionException | InterruptedException | IOException e)
        {
            throw new RuntimeException(e);
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
