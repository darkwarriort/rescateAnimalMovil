package com.fundacionrescate.rescata.util;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.transition.Slide;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.fundacionrescate.rescata.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * Created by jogan1075 on -09-17.
 */
public class ActivityUtils {

    private ActivityUtils() {
    }


    public static void hideKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct
        // window token from it.
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we
        // can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * method for hide keyboard
     *
     * @param fragment
     */
    public static void hideKeyboard(Fragment fragment) {
        try {
            AppCompatActivity activity = (AppCompatActivity) fragment.getActivity();
            if (activity != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                // Find the currently focused view, so we can grab the correct
                // window token from it.
                View view = activity.getCurrentFocus();
                // If no view currently has focus, create a new one, just so we
                // can grab a window token from it
                if (view == null) {
                    view = new View(activity);
                }
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } else {
//                Activity activity1 = AppContextPacifico.getInstance().getActivity();
                Activity activity1 =  fragment.getActivity();
                InputMethodManager inputMethodManager = (InputMethodManager) activity1.getSystemService(Activity.INPUT_METHOD_SERVICE);
                // Find the currently focused view, so we can grab the correct
                // window token from it.
                View view = activity1.getCurrentFocus();
                // If no view currently has focus, create a new one, just so we
                // can grab a window token from it
                if (view == null) {
                    view = new View(activity1);
                }
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ex) {
//            Clog.e(ex);
        }


    }


    /**
     * change tint for imageview
     *
     * @param image
     * @param context
     * @param color
     */
    public static void changeColorDrawable(ImageView image, Context context, int color) {
        ImageViewCompat.setImageTintList(image, ColorStateList.valueOf(ContextCompat.getColor
                (context, color)));

    }

    /**
     * validate if edittext is empty
     *
     * @param editText
     * @return
     */
    public static boolean editTextIsEmpty(EditText editText) {

        String input = editText.getText().toString().trim();
        return input.length() == 0;

    }


    /**
     * validate valid mail
     *
     * @param target
     * @return bool valid
     */
    public final static boolean editTextisValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * show native dialog easily
     *
     * @param context
     * @param tittle
     * @param message
     * @param positiveBtn
     */
    public static void dialog(Context context, String tittle, String message, String positiveBtn) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(tittle)
                .setMessage(message)
                .setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }

    /**
     * @param day
     * @param month
     * @param year
     * @param formated
     * @return formatted date to show to user or in format to send in request
     **/
    public static String stringToDate(int day, int month, int year, boolean formated) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (formated)
            format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        String strDate = format.format(calendar.getTime());
        return strDate == null ? "" : strDate;
    }


    /**
     * @param activity
     * @return Bitmap photo of the currect screen
     */
    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * Convert image in blurimage
     *
     * @param context
     * @param image
     * @return
     */
    public static Bitmap blur(Context context, Bitmap image) {
        final float BITMAP_SCALE = 0.4f;
        final float BLUR_RADIUS = 24.5f;
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }


    /**
     * Custom Blur Dialog
     *
     * @param context
     * @param backgroundColorDialog
     * @param titleTextDialog
     * @param bodyTextDialog
     * @param buttonTextDialog
     * @param accept
     * @param callback
     * @param blurredBitmap
     */

    public static void customDialogBlur(Context context, int backgroundColorDialog, String titleTextDialog, String bodyTextDialog, String buttonTextDialog, final boolean accept, final FinishDialog callback, Bitmap blurredBitmap) {

        final Dialog customDialog = new Dialog(context, R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.fragment_modal_dialog);
        LinearLayout root = (LinearLayout) customDialog.findViewById(R.id.modal_linear_container);
        FrameLayout container = (FrameLayout) customDialog.findViewById(R.id.container_modal_dialog);
        container.setBackground(new BitmapDrawable(context.getResources(), blurredBitmap));
//        root.setBackgroundColor(ContextCompat.getColor(context, backgroundColorDialog));
        TextView tittle = (TextView) customDialog.findViewById(R.id.title_modal);
        tittle.setText(titleTextDialog);
        TextView body = (TextView) customDialog.findViewById(R.id.body_modal);
        body.setText(bodyTextDialog);
        Button button = (Button) customDialog.findViewById(R.id.button_modal);
        button.setText(buttonTextDialog);
        ((ImageView) customDialog.findViewById(R.id.modal_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.successModalDialog(!accept);

                customDialog.dismiss();


            }
        });
        (button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                callback.successModalDialog(accept);
                customDialog.dismiss();


            }
        });

        customDialog.show();

    }

    /**
     * dialog for informative icons
     *
     * @param context
     * @param titleTextDialog
     * @param bodyTextDialog
     * @param textBelowBody
     * @param blurredBitmap
     */
    public static void showToolTipDialogBlur(Context context, String titleTextDialog, String bodyTextDialog, String textBelowBody, Bitmap blurredBitmap, boolean animation, boolean alignLeft) {

        final Dialog customDialog = new Dialog(context, R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.modal_informative);
        FrameLayout container = (FrameLayout) customDialog.findViewById(R.id.informative_container);
        container.setBackground(new BitmapDrawable(context.getResources(), blurredBitmap));
        TextView tittle = (TextView) customDialog.findViewById(R.id.informative_title);
        tittle.setText(titleTextDialog);
        TextView body = (TextView) customDialog.findViewById(R.id.informative_body_text);
        body.setText(bodyTextDialog);
        TextView bodyBelowTextView = (TextView) customDialog.findViewById(R.id.informative_text_below_body);
//        LottieAnimationView animationView = customDialog.findViewById(R.id.informative_animation);
        if (textBelowBody != null && !"".equals(textBelowBody)) {
            bodyBelowTextView.setVisibility(View.VISIBLE);
            bodyBelowTextView.setText(textBelowBody);
        }
        if (alignLeft) {
            body.setGravity(Gravity.LEFT);
            body.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.0f, context.getResources().getDisplayMetrics()), 1.0f);
        }

        if (animation) {
//            animationView.setVisibility(View.VISIBLE);
//            animationView.setAnimation(R.raw.fingerprint);
//            animationView.playAnimation();
        }
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();


            }
        });
        customDialog.show();

    }

    /**
     * Interface for BlurEffect dialog when finish
     */
    public interface FinishDialog {
        void successModalDialog(Boolean respuesta);
    }



    /**
     * @param year
     * @param month
     * @param day
     * @return age
     */
    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    /**
     * get current or minimun date
     *
     * @param isminDate
     * @return Date
     */
    public static String getDate(boolean isminDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        if (!isminDate)
            date = new Date();
        else
            date = new Date(0L);
        return dateFormat.format(date).toString();
    }


}
