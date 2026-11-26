package com.financampus.app.presentation.components
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
@Composable fun NavGlyph(index:Int) {
 val color=LocalContentColor.current
 Canvas(Modifier.size(24.dp)) {
 val w=size.width; val h=size.height; val stroke=2.dp.toPx()
 fun line(x:Float,y:Float,a:Float,b:Float) = drawLine(color,Offset(w*x,h*y),Offset(w*a,h*b),stroke)
 when(index) {
 0 -> { line(.1f,.45f,.5f,.1f); line(.5f,.1f,.9f,.45f); line(.22f,.4f,.22f,.9f); line(.78f,.4f,.78f,.9f); line(.22f,.9f,.78f,.9f) }
 1 -> { line(.1f,.3f,.9f,.3f); line(.9f,.3f,.7f,.1f); line(.9f,.3f,.7f,.5f); line(.9f,.7f,.1f,.7f); line(.1f,.7f,.3f,.5f); line(.1f,.7f,.3f,.9f) }
 2 -> { line(.2f,.9f,.2f,.55f); line(.5f,.9f,.5f,.15f); line(.8f,.9f,.8f,.35f) }
 else -> { drawCircle(color,w*.4f,style=Stroke(stroke)); drawCircle(color,w*.23f,style=Stroke(stroke)); drawCircle(color,w*.06f) }
 }
 }
}
