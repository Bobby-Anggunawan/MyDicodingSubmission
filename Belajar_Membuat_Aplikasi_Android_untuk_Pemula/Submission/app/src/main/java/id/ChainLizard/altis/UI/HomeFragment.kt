package id.ChainLizard.altis.UI

import HomeListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.MaterialSharedAxis
import id.ChainLizard.altis.MainActivity
import id.ChainLizard.altis.R



class HomeFragment : Fragment() {

    lateinit var myRecyclerView: RecyclerView
    val alist: ArrayList<HomeListAdapter.RowData> = arrayListOf(
        HomeListAdapter.RowData(R.drawable.ikan_arwana, "Arwana Asia", "Scleropages formosus", "Karnivora", "Sulit", "file:///android_asset/arwana.html"),
        HomeListAdapter.RowData(R.drawable.ikan_botia, "Botia Badut", "Chromobotia macracanthus", "Omnivora", "Sedang", "file:///android_asset/botia.html"),
        HomeListAdapter.RowData(R.drawable.ikan_cupang, "Cupang", "Betta splendens", "Karnivora", "Mudah", "file:///android_asset/cupang.html"),
        HomeListAdapter.RowData(R.drawable.ikan_discus, "Discus", "Symphysodon discus", "Omnivora", "Sedang", "file:///android_asset/discus.html"),
        HomeListAdapter.RowData(R.drawable.ikan_guppy, "Guppy", "Poecilia reticulata", "Omnivora", "Mudah", "file:///android_asset/guppy.html"),
        HomeListAdapter.RowData(R.drawable.ikan_komet, "Komet", "Carassius auratus", "Omnivora", "Mudah", "file:///android_asset/komet.html"),
        HomeListAdapter.RowData(R.drawable.ikan_louhan, "Louhan", "-", "Karnivora", "Mudah", "file:///android_asset/louhan.html"),
        HomeListAdapter.RowData(R.drawable.ikan_mas_koki, "Mas Koki", "Carassius auratus", "Omnivora", "Sedang", "file:///android_asset/mas koki.html"),
        HomeListAdapter.RowData(R.drawable.ikan_molly, "Molly", "Poecilia sphenops", "Omnifora", "Mudah", "file:///android_asset/molly.html"),
        HomeListAdapter.RowData(R.drawable.ikan_neon_tetra, "Neon Tetra", "Paracheirodon innesi", "Omnivora", "Mudah", "file:///android_asset/neon tetra.html"),
        HomeListAdapter.RowData(R.drawable.ikan_palmas, "Palmas", "Polypterus palmas", "Karnivora", "Sedang", "file:///android_asset/palmas.html")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        myRecyclerView = root.findViewById(R.id.main_list)
        SetAdapter()

        //set menu
        root.findViewById<MaterialToolbar>(R.id.topAppBar).setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    // Handle favorite icon press
                    MainActivity.navController.navigate(R.id.fragment_profil)
                    true
                }
                else -> false
            }
        }

        return root
    }


    fun SetAdapter(){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = HomeListAdapter(alist)
        myRecyclerView.adapter = ListAdapter

        ListAdapter.onItemClick = {
            ArticleFragment.title = it.nama
            ArticleFragment.loadUrl = it.link
            MainActivity.navController.navigate(R.id.fragment_artikel)
        }
    }
}