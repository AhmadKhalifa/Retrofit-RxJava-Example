package orangelabs.com.retrofitexample.src.data.service;

import retrofit.RestAdapter;

/**
 * Created by khalifa on 21/08/17.
 */

class ServiceFactory {
    static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
                .build();
        return restAdapter.create(clazz);
    }
}
