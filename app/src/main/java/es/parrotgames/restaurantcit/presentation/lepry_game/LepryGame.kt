package es.parrotgames.restaurantcit.presentation.lepry_game

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import es.parrotgames.restaurantcit.R
import es.parrotgames.restaurantcit.databinding.LepryGameBinding
import kotlin.random.Random

const val START_VALUE = 200
const val ROUND_BET = 20
const val BIG_PRIZE = 100
const val SMALL_PRIZE = 30

class LepryGame: AppCompatActivity() {

    private lateinit var binding: LepryGameBinding

    private var lepryElements: Array<LepryElements> =
        arrayOf(
            LepryElements.A,
            LepryElements.BEER,
            LepryElements.COIN,
            LepryElements.J,
            LepryElements.K,
            LepryElements.POUCH
        )
    private var points: Int = START_VALUE

    private var element1: LepryElements? = null
    private var element2: LepryElements? = null
    private var element3: LepryElements? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LepryGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.points.text = points.toString()
        binding.cd1.setOnClickListener {
            it.isEnabled = false
            element1 = getSymbol()
            anime(binding.view1, binding.iv1, element1!!)
        }
        binding.cd2.setOnClickListener {
            it.isEnabled = false
            element2 = getSymbol()
            anime(binding.view2, binding.iv2, element2!!)
        }
        binding.cd3.setOnClickListener {
            it.isEnabled = false
            element3 = getSymbol()
            anime(binding.view3, binding.iv3, element3!!)
        }
        binding.btnRefresh.visibility = View.INVISIBLE
        binding.btnRefresh.setOnClickListener {
            binding.btnRefresh.visibility = View.INVISIBLE
            startGame()
            roundBet()
        }
    }

    private fun checkPrize() {
        if (element1 == null || element2 == null || element3 == null) return

        val countNew = when {
            element1 == element2 && element1 == element3 -> {
                points + BIG_PRIZE
            }
            element1 == element2 || element1 == element3 || element2 == element3 -> {
                points + SMALL_PRIZE
            }
            else -> {
                points
            }
        }

        points = countNew
        binding.points.text = points.toString()
        checkGameScore()
    }

    private fun checkGameScore() {
        if (points - ROUND_BET < 0) {
            binding.btnRefresh.visibility = View.INVISIBLE
            goToFinishGame()
        } else {
            binding.btnRefresh.visibility = View.VISIBLE
        }
    }

    private fun roundBet() {
        if (points - ROUND_BET >= 0) {
            points -= ROUND_BET
            binding.points.text = points.toString()
        } else {
            goToFinishGame()
        }
    }

    private fun setImage(background: View, imageView: ImageView, currentIcon: LepryElements) {
        background.setBackgroundDrawable(ContextCompat.getDrawable(background.context, R.drawable.btn_gradient))
        imageView.setImageResource(currentIcon.res)
        checkPrize()
    }

    private fun startGame() {
        binding.cd1.isEnabled = true
        binding.cd2.isEnabled = true
        binding.cd3.isEnabled = true

        binding.iv1.visibility = View.INVISIBLE
        binding.iv2.visibility = View.INVISIBLE
        binding.iv3.visibility = View.INVISIBLE

        element1 = null
        element2 = null
        element3 = null

        binding.view1.background =
            ContextCompat.getDrawable(binding.cd1.context, R.drawable.lepry_loading)
        binding.view2.background =
            ContextCompat.getDrawable(binding.cd2.context, R.drawable.lepry_loading)
        binding.view3.background =
            ContextCompat.getDrawable(binding.cd3.context, R.drawable.lepry_loading)

        binding.points.text = points.toString()

    }

    private fun getSymbol(): LepryElements {
        val index = Random.nextInt(0, lepryElements.size)
        return lepryElements[index]
    }

    private fun goToFinishGame() {
        val i = Intent(this, LepryFinishGame::class.java)
        startActivity(i)
        finish()
    }

    private fun anime(background: View, imageView: ImageView, currentIcon: LepryElements): Animator {
        val start = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                val v = it.animatedValue as Float
                background.scaleX = (1f - v) / 1f
            }
            doOnEnd {
                setImage(background, imageView, currentIcon)
            }
        }
        val finish= ValueAnimator.ofFloat(1f, 0f).apply {
            addUpdateListener {
                val v = it.animatedValue as Float
                background.scaleX = (1f - v) / 1f
            }
            doOnEnd {

            }
        }
        return AnimatorSet().apply {
            playSequentially(start, finish)
            duration = 300
            doOnEnd { imageView.visibility = View.VISIBLE }
            start()
        }
    }
}