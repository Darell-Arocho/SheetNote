
package com.example.sketch_1;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PdfDocumentAdapter extends PrintDocumentAdapter {

    private List<CanvasView> canvasList;

    public PdfDocumentAdapter(List<CanvasView> canvasList) {
        this.canvasList = canvasList;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder("notebook.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(canvasList.size());

        PrintDocumentInfo info = builder.build();
        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        OutputStream outputStream = new FileOutputStream(destination.getFileDescriptor());

        try {
            PdfDocument document = new PdfDocument();
            for (int i = 0; i < canvasList.size(); i++) {
                CanvasView canvasView = canvasList.get(i);
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(canvasView.getWidth(), canvasView.getHeight(), i).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                canvasView.draw(page.getCanvas());
                document.finishPage(page);
            }
            document.writeTo(outputStream);
            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        } catch (IOException e) {
            callback.onWriteFailed(e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
