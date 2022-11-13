package com.example.whatsappclone1;

import android.net.Uri;

public class statusModel {

    String path,fileName;
    Uri uri;

    public statusModel(String path, String fileName, Uri uri) {
        this.path = path;
        this.fileName = fileName;
        this.uri = uri;
    }

    public statusModel() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
