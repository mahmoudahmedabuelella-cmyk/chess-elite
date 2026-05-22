package com.chess.elite.ui.board

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ChessBoard(
    boardState: Map<Square, ChessPiece>,
    onMoveRequested: (from: Square, to: Square) -> Unit,
    isFlipped: Boolean = false,
    boardTheme: BoardTheme = BoardTheme.ClassicWooden
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
        val squareSizePx = constraints.maxWidth.toFloat() / 8f
        
        // Draw alternate Grid Board
        Canvas(modifier = Modifier.fillMaxSize()) {
            for (file in 0..7) {
                for (rank in 0..7) {
                    val isLight = (file + rank) % 2 != 0
                    drawRect(
                        color = if (isLight) boardTheme.lightColor else boardTheme.darkColor,
                        topLeft = Offset(file * squareSizePx, rank * squareSizePx)
                    )
                }
            }
        }
        
        // Drag and Drop pointer input detectors & Piece renders go here
    }
}