package com.naorandd.testwifidirect.util

import android.graphics.drawable.AnimationDrawable
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Viewにアニメーションを設定するクラス
 */
class AnimationUtil {
    companion object {
        /**
         * constraintLayoutにアニメーションを設定するメソッド
         * @param constraintLayout アニメーションを設定するconstraintLayout
         * @param EnterAnimationDuration アニメーションのEnterDuration
         * @param ExitAnimationDuration アニメーションのExitDuration
         */
        fun animateConstraintLayout(
            constraintLayout: ConstraintLayout,
            EnterAnimationDuration: Int,
            ExitAnimationDuration: Int
        ) {
            val animationDrawable =
                constraintLayout.background as AnimationDrawable
            animationDrawable.setEnterFadeDuration(EnterAnimationDuration)
            animationDrawable.setExitFadeDuration(ExitAnimationDuration)
            animationDrawable.start()
        }
    }
}