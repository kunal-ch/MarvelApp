package com.kc.marvelapp.presentation.character_listing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.util.Utils
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CharacterItem(
    character: ComicCharacter,
    modifier: Modifier = Modifier
) {
    Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImage(
            imageModel = character.thumbnail.path+"."+character.thumbnail.extension,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = character.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                /*Text(
                    text = character.name,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onBackground
                )*/
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = Utils.getDate(character.modified),
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}