package ru.d10xa.passwordgenerator.svg
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given

object RefreshSvg {
  def apply() =
    svg.svg(
      svg.cls := "svg",
      svg.width := "800px",
      svg.height := "800px",
      svg.viewBox := "0 0 24 24",
      svg.fill := "none",
      svg.xmlns := "http://www.w3.org/2000/svg",
      svg.pointerEvents := "none",
      svg.path(
        svg.d := "M21 3L15.6 3C15.2686 3 15 3.26863 15 3.6V3.6L15 9",
        svg.stroke := "#323232",
        svg.strokeWidth := "2",
        svg.strokeLineCap := "round",
        svg.strokeLineJoin := "round"
      ),
      svg.path(
        svg.d := "M15.5 3.5C18.7983 4.80851 21 8.29825 21 12C21 16.8715 16.9706 21 12 21C7.02944 21 3 16.8715 3 12C3 8.73514 4.80989 5.52512 7.5 4",
        svg.stroke := "#323232",
        svg.strokeWidth := "2",
        svg.strokeLineCap := "round",
        svg.strokeLineJoin := "round"
      )
    )

}
