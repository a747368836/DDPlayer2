package top.bilibililike.player.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import top.bilibililike.player.R

class RecommendFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

            val root = inflater.inflate(R.layout.fragment_home, container, false)

            return root
    }


}