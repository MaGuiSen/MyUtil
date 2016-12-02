package lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ma.myutil.R;


public class LoadingDialog
{
    private Dialog mInnerDialog;

    public LoadingDialog(Context context, String message, boolean cancelable, boolean otoCancelable) {

        View view = View.inflate(context, R.layout.dialog_loading, null);
        TextView title = (TextView) view.findViewById(R.id.id_dialog_loading_msg);
        title.setText(message);
        mInnerDialog = new Dialog(context, R.style.loading_dialog);
        mInnerDialog.setContentView(view);
        mInnerDialog.setCancelable(cancelable);
        mInnerDialog.setCanceledOnTouchOutside(otoCancelable);
    }

    public void show() {
        if (mInnerDialog != null && !mInnerDialog.isShowing()) {
            mInnerDialog.show();
        }
    }

    public void dismiss() {
        if (mInnerDialog != null && mInnerDialog.isShowing()) {
            mInnerDialog.dismiss();
        }
    }
}
