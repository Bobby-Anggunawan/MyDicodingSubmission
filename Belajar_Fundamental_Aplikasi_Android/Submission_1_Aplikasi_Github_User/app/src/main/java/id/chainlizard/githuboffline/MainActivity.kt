package id.chainlizard.githuboffline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        var alist: ArrayList<UserListAdapter.RowData> = arrayListOf()
        lateinit var navController: NavController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        alist.addAll(loadList())
    }

    fun loadList(itemCount: Int = 10): ArrayList<UserListAdapter.RowData>{
        val ret: ArrayList<UserListAdapter.RowData> = arrayListOf()
        val txt = LoadData("githubuser.json")
        val jsonObj = JSONObject(txt).getJSONArray("users")
        for(x in 0..itemCount-1){
            val aData = jsonObj.getJSONObject(x)
            ret.add(UserListAdapter.RowData(
                    aData.getString("username"),
                    aData.getString("name"),
                    aData.getString("avatar"),
                    aData.getString("company"),
                    aData.getString("location"),
                    aData.getInt("repository"),
                    aData.getInt("follower"),
                    aData.getInt("following")
            ))
        }
        return ret
    }

    fun LoadData(inFile: String): String {
        var tContents: String = ""
        try {
            val stream: InputStream = assets.open(inFile!!)
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            //todo
        }
        return tContents
    }
}