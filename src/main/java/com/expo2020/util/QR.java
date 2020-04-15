package com.expo2020.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author kristoffer
 * Klasse for å generere QR-koder til stands.
 *
 */
public class QR {

    private static final int WIDTH = 350;
    private static final int HEIGHT = 350;

    /**
     *
     * @param url URL som legges inn i QR-koden.
     * @throws WriterException Kaster feil ved enkoding av QR-koden.
     * @throws IOException Signaliserer en I/O-feil.
     * @return stream
     */

    public static byte[] generateQrImage(String url)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "JPG", stream);
        return stream.toByteArray();
    }
}
