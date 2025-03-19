package com.example.qrstudentmanagerapp.ui.QR;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.IOException;

public class GennQR {

    private OkHttpClient client;

    public GennQR() {
        // Inicializar el cliente HTTP
        client = new OkHttpClient();
    }

    /**
     * Genera un código QR usando la API de QR Server.
     *
     * @param data   El contenido del código QR (URL, texto, etc.).
     * @param size   Tamaño del código QR (ancho x alto, en píxeles).
     * @param callback Callback para manejar la respuesta (Bitmap o error).
     */
    public void generateQRCode(String data, int size, QRCodeCallback callback) {
        // Construir la URL de la API
        String url = "https://api.qrserver.com/v1/create-qr-code/?data=" + data + "&size=" + size + "x" + size;

        // Crear una solicitud GET
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Realizar la solicitud de forma asíncrona
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Manejar errores
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Convertir la respuesta a un Bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    callback.onSuccess(bitmap);
                } else {
                    // Manejar errores
                    callback.onError("Error: " + response.code());
                }
            }
        });
    }

    /**
     * Interfaz para manejar la respuesta del código QR.
     */
    public interface QRCodeCallback {
        void onSuccess(Bitmap qrCodeBitmap);
        void onError(String errorMessage);
    }
}