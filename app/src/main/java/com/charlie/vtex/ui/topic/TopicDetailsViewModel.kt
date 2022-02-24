package com.charlie.vtex.ui.topic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlie.vtex.base.BaseViewModel
import com.charlie.vtex.repository.BaseRepository
import com.charlie.vtex.repository.TopicItemDetailsRepository
import com.elvishew.xlog.XLog
import com.imhanjie.v2ex.api.model.Reply
import com.imhanjie.v2ex.api.model.Topic
import kotlinx.coroutines.launch

class TopicDetailsViewModel : BaseViewModel() {

    val topicLiveData = MutableLiveData<Topic>(null)

    var isLoadingTopic = false

    override val repository: TopicItemDetailsRepository = TopicItemDetailsRepository()

    fun getTopic(topicId: Long) {
        if (isLoadingTopic) {
            return
        }
        viewModelScope.launch {
            try {
                if (isLoadingTopic) {
                    return@launch
                }
                isLoadingTopic = true
                Log.d("asdqwe", "load topic = $topicId")
                val topic = repository.loadTopic(topicId = topicId, 0)
                topicLiveData.value = topic
            } catch (e: Exception) {
                XLog.e(e)
            } finally {
                isLoadingTopic = false
            }
        }

//         topicLiveData.value = Topic(
//            id = 835643,
//            title = "以前没发现 form 表单这个特性",
//            nodeName = "fe",
//            nodeTitle = "前端开发",
//            userName = "waiaan",
//            userAvatar = "https://cdn.v2ex.com/avatar/a703/deb0/206370_large.png?m=1637223423",
//            createTime = "2 小时 7 分钟前",
//            click = 768,
//            richContent = """
//                .trimIndent()< pre > < code class = "language-html" > & lt;form&gt;
//        &lt;input type ="button" onclick = "submit()" value = "提交"/&gt;
//        &lt;/form&gt;
//        </code></pre>
//        <p > 在 html 里写如上代码，未定义任何 submit 方法，点击按钮后 form 表单会直接提交。 如果覆写 form 元素自带的 submit 方法</p>
//        <pre > < code class="language-js">HTMLFormElement.prototype.submit = function(){
//        console.log(
//            123
//        )
//    }
//        </code></pre>
//        <p > 此时点击按钮后 form 表单不再提交，控制台打印出 123 。 chrome 、ff 、edge 会如此，ie11 不会，这是浏览器新功能还是规范里规定的？</p>
//        """,
//            emptyList(),
//            listOf(
//                Reply(
//                    0,
//                    "https://cdn.v2ex.com/avatar/a703/deb0/206370_large.png?m=1637223423",
//                    "aaa",
//                    "里写如上代码，未定义任何 submit 方法，点击按钮后 for\n" +
//                            "任何 submit 方法，点击按钮后 for",
//                    "2 小时",
//                    0, false, 1
//                ), Reply(
//                    0,
//                    "https://cdn.v2ex.com/avatar/a703/deb0/206370_large.png?m=1637223423",
//                    "aaa",
//                    "里写如上代码，未定义任何 submit 方法，点击按钮后 for\n" +
//                            "任何 submit 方法，点击按钮后 for",
//                    "2 小时",
//                    0, false, 1
//                ), Reply(
//                    0,
//                    "https://cdn.v2ex.com/avatar/a703/deb0/206370_large.png?m=1637223423",
//                    "aaa",
//                    "里写如上代码，未定义任何 submit 方法，点击按钮后 for\n" +
//                            "任何 submit 方法，点击按钮后 for",
//                    "2 小时",
//                    0, false, 1
//                ),
//                Reply(
//                    0,
//                    "https://cdn.v2ex.com/avatar/a703/deb0/206370_large.png?m=1637223423",
//                    "aaa",
//                    "里写如上代码，未定义任何 submit 方法，点击按钮后 for\n" +
//                            "任何 submit 方法，点击按钮后 for",
//                    "2 小时",
//                    0, false, 1
//                )
//            ),
//            0, 10,
//            "", false, "", false, false
//        )
    }

}