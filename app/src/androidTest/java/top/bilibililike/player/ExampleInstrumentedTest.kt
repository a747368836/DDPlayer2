package top.bilibililike.player

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import top.bilibililike.player.common.sign.Signer

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("top.bilibililike.ddplayer", appContext.packageName)
        val str = "access_key=714c96f2612cd82609c480ee85b43211&actionKey=appkey&appkey=1d8b6e7d45233436&build=5521100&channel=xiaomi&device=android&mobi_app=android&platform=android&room_id=10209381&statistics=%7B%22appId%22%3A1%2C%22platform%22%3A3%2C%22version%22%3A%225.52.1%22%2C%22abtest%22%3A%22890%22%7D&ts=1579088024"
        print(Signer.getSign(str))
    }
}
