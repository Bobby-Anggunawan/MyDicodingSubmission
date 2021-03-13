package id.chainlizard.githubsearch.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.chainlizard.githubsearch.R

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val backdrop: LinearLayout = root.findViewById(R.id.backdrop)
        val sheetBehavior = BottomSheetBehavior.from(backdrop)
        sheetBehavior.setFitToContents(false)
        sheetBehavior.setHideable(true)
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        return root
    }
}