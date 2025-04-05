package com.example.gpaCalculator.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.gpaCalculator.model.Module;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ModuleFetcher {
    private static final String API_URL = "https://num.univ-biskra.dz/psp/formations/get_modules_json?sem=1&spec=184";
    private OkHttpClient client;
    private Gson gson;

    public ModuleFetcher() {
        client = new OkHttpClient();
        gson = new Gson();
    }

    public List<Module> fetchModules() throws IOException {
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String jsonData = response.body().string();
            Type moduleListType = new TypeToken<List<Module>>() {}.getType();
            return gson.fromJson(jsonData, moduleListType);
        }
    }
}
