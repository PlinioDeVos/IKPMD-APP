package nl.pdevos.ikpmd.models;

import android.content.res.Resources;

import nl.pdevos.ikpmd.R;

public class ColorConverter {
    private Resources resources;

    public ColorConverter(Resources resources) {
        this.resources = resources;
    }

    public int getIntColorFromColorName(String colorName) {
        int color;

        switch (colorName.toLowerCase()) {
            case ("rood"):
                color = R.color.red;
                break;
            case ("geel"):
                color = R.color.yellow;
                break;
            case ("groen"):
                color = R.color.green;
                break;
            default:
                color = R.color.white;
                break;
        }

        return resources.getColor(color);
    }
}
