package nurisezgin.com.glory;

import android.content.Context;
import android.content.Intent;

/**
 * Created by nuri on 31.07.2018
 */
public interface Requester {

    void request(Request request);

    class Default implements Requester {

        private final Context context;

        public Default(Context context) {
            this.context = context;
        }

        @Override
        public void request(Request request) {
            Intent intent = new Intent(context, GloryActivity.class);
            intent.putExtra(GloryActivity.REQ_KEY, request);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


}
