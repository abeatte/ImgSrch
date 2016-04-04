package com.beatte.art.imgsrch;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.beatte.art.imgsrch.models.GettyImage;
import com.beatte.art.imgsrch.models.GettyImages;
import com.beatte.art.imgsrch.rest.RESTService;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class ImageGetIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_GET_IMAGES =
            ImageGetIntentService.class.getSimpleName() + ".action.GET_IMAGES";
    public static final String ACTION_GET_IMAGES_SUCCESS =
            ImageGetIntentService.class.getSimpleName() + ".action.GET_IMAGES_SUCCESS";
    public static final String ACTION_GET_IMAGES_FAILURE =
            ImageGetIntentService.class.getSimpleName() + ".action.GET_IMAGES_SUCCESS";
    public static final String EXTRA_IMAGES =
            ImageGetIntentService.class.getSimpleName() + ".extra.IMAGES";
    public static final String EXTRA_ERROR_MESSAGE =
            ImageGetIntentService.class.getSimpleName() + ".extra.ERROR_MESSAGE";
    public static final String EXTRA_SEARCH_TEXT =
            ImageGetIntentService.class.getSimpleName() + ".extra.SEARCH_TEXT";

    public ImageGetIntentService() {
        super("ImageGetIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void handleActionGetImages(final Context context, String searchText) {
        new RESTService()
                .getImageService()
                .search(BuildConfig.GETTY_KEY, searchText)
                .enqueue(new Callback<GettyImages>() {
            @Override
            public void onResponse(Response<GettyImages> response) {
                ArrayList<GettyImage> list = new ArrayList<>(Arrays.asList(response.body().getImages()));
                context.sendBroadcast(new Intent(ACTION_GET_IMAGES_SUCCESS)
                        .putParcelableArrayListExtra(EXTRA_IMAGES, list));
            }

            @Override
            public void onFailure(Throwable t) {
                context.sendBroadcast(new Intent(ACTION_GET_IMAGES_FAILURE)
                        .putExtra(EXTRA_ERROR_MESSAGE, t.getMessage()));
            }
        });
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_IMAGES.equals(action)) {
                final String searchText = intent.getStringExtra(EXTRA_SEARCH_TEXT);
                handleActionGetImages(this, searchText);
            }
        }
    }
}
