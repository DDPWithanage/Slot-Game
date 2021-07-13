package com.example.slotgame;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CasinoGame extends FrameLayout {

    private static int ANIMATION_DUR = 150;
    ImageView current_image, next_image;
    int last_result = 0, old_value = 0;
    Event event;

    public void setEvent(Event event) {
        this.event = event;
    }

    public CasinoGame(Context context) {
        super(context);
        init(context);
    }

    public CasinoGame(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.activity_casino_game, this);
        current_image = (ImageView)getRootView().findViewById(R.id.current_image);
        next_image = (ImageView)getRootView().findViewById(R.id.next_image);

        next_image.setTranslationY(getHeight());
    }

    public void setRandomImage(int image, int rotate_count){
        current_image.animate().translationY(-getHeight()).setDuration(ANIMATION_DUR).start();
        next_image.setTranslationY(next_image.getHeight());
        next_image.animate().translationY(0)
                .setDuration(ANIMATION_DUR)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setImage(current_image, old_value % 11); //Because we have 11 images
                        current_image.setTranslationY(0);
                        if(old_value != rotate_count){
                            //If old value still mot equal to rotate count, we still roll
                            setRandomImage(image,rotate_count);
                            old_value++;

                        }else{ //if rotate successfull
                            last_result = 0;
                            old_value = 0;
                            setImage(next_image, image);
                            event.eventEnd(image % 11, rotate_count);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void setImage(ImageView image_view, int value) {
        if(value == Util.ZERO)
            image_view.setImageResource(R.drawable.zero);
        else if(value == Util.ONE)
            image_view.setImageResource(R.drawable.one);
        else if(value == Util.TWO)
            image_view.setImageResource(R.drawable.two);
        else if(value == Util.THREE)
            image_view.setImageResource(R.drawable.three);
        else if(value == Util.FOUR)
            image_view.setImageResource(R.drawable.four);
        else if(value == Util.FIVE)
            image_view.setImageResource(R.drawable.five);
        else if(value == Util.SIX)
            image_view.setImageResource(R.drawable.six);
        else if(value == Util.SEVEN)
            image_view.setImageResource(R.drawable.seven);
        else if(value == Util.EIGHT)
            image_view.setImageResource(R.drawable.eight);
        else if(value == Util.NINE)
            image_view.setImageResource(R.drawable.nine);
        else
            image_view.setImageResource(R.drawable.ten);

        //compare result
        image_view.setTag(value);
        last_result = value;

    }

    public int getValue(){
        return Integer.parseInt(next_image.getTag().toString());
    }
}