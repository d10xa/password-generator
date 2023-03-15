package ru.d10xa.passwordgenerator

import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom

object Clipboard {
    def copy(value: String): Unit = {
        val fakeElement = createFakeElement(value)
        val fakeElementRoot = render(dom.document.body, fakeElement)
        fakeElement.ref.select()
        fakeElement.ref.setSelectionRange(0, fakeElement.ref.value.length())
        dom.document.execCommand("copy")
        fakeElementRoot.unmount()
    }
    
    private def createFakeElement(v: String): ReactiveHtmlElement[dom.html.TextArea] = {
        //https://github.com/zenorocha/clipboard.js/blob/master/src/common/create-fake-element.js
        val yPosition = scala.math.max(dom.window.pageYOffset, dom.document.documentElement.scrollTop)
        val el = textArea(
            fontSize := "12pt",
            border := "0",
            padding := "0",
            margin := "0",
            position := "absolute",
            left := "-9999px",
            top := s"${yPosition}px",
            value := v
        )
        el
    }
}
