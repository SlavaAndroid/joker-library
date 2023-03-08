package es.parrotgames.restaurantcit.presentation.lepry_game

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import es.parrotgames.restaurantcit.R
import es.parrotgames.restaurantcit.databinding.LepryFinishGameBinding

class LepryFinishGame: AppCompatActivity() {

    private lateinit var binding: LepryFinishGameBinding
    private var animator: Animator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LepryFinishGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        firstAnim()

       binding.btnReturn.setOnClickListener {
           val i = Intent(this, LepryGame::class.java)
           startActivity(i)
           finish()
       }
    }

    private fun firstAnim() {
        animator = AnimatorSet().apply {
            playSequentially(
                ObjectAnimator.ofFloat(findViewById(R.id.iv1), "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(findViewById(R.id.iv1), "alpha", 0f, 1f)
            )
            duration = 500
            start()
            doOnEnd { secondAnim() }
        }
    }
    private fun secondAnim() {
        animator = AnimatorSet().apply {
            playSequentially(
                ObjectAnimator.ofFloat(findViewById(R.id.iv2), "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(findViewById(R.id.iv2), "alpha", 0f, 1f)
            )
            duration = 500
            start()
            doOnEnd { thirdAnim() }
        }
    }
    private fun thirdAnim() {
        animator = AnimatorSet().apply {
            playSequentially(
                ObjectAnimator.ofFloat(findViewById(R.id.iv3), "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(findViewById(R.id.iv3), "alpha", 0f, 1f)
            )
            duration = 500
            start()
            doOnEnd { firstAnim() }
        }
    }
}