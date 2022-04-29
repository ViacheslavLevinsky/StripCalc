package sample;


public class CalcModel {

    private final float MAGNETIC_CONST = (float) (12.56 * Math.pow(10, -7));

    public float getConductWidth(float waveResist, float subThickness, float dielectricConst) {
        float conductWidth = 0;
        float A;
        if (waveResist != 0 && subThickness != 0 && dielectricConst != 0) {
            A = (float) ((waveResist / 60) * Math.sqrt((dielectricConst + 1) / 2) +
                    ((dielectricConst - 1) / (dielectricConst + 1)) * (0.23 + 0.11 / dielectricConst));
            conductWidth = (float) ((8 * Math.exp(A) * subThickness) / (Math.exp(2 * A) - 2));

            //conductWidth = (float) ((7.463 * subThickness) / (Math.exp(waveResist * (Math.sqrt(0.4755 * dielectricConst + 0.67) / 60))) * 1.25 * stripThickness);
        }
        return conductWidth;
    }

    public float getEffectDielConst(float dielectricConst, float subThickness, float conductWidth) {
        float effectDielConst = 0;
        if (conductWidth != 0 && dielectricConst != 0 && subThickness != 0) {
            effectDielConst = (float) ((dielectricConst + 1) / 2 + (dielectricConst - 1) / 2 * Math.pow((1 + 10 * subThickness / conductWidth), -0.5));
        }
        return effectDielConst;
    }

    public float getWaveLenght(float waveLenghtFreeSpace, float effectDielConst) {
        float waveLenght = 0;
        if (waveLenghtFreeSpace != 0 && effectDielConst != 0) {
            waveLenght = (float) (waveLenghtFreeSpace / Math.sqrt(effectDielConst));
        }
        return waveLenght;
    }

    public float getWaveLenghtFreeSpace(float freq) {
        float waveLenghtFreeSpace = 0;
        if (freq != 0) {
            waveLenghtFreeSpace = (float) ((3 * Math.pow(10, 8)) / freq);
        }
        return waveLenghtFreeSpace;
    }

    public float getDielectricLoss(float dielectricConst, float effectDielConst, float lossTanget, float waveLenghtFreeSpace) {
        float dielectricLoss = 0;
        if (dielectricConst != 0 && lossTanget != 0 && waveLenghtFreeSpace != 0) {
            dielectricLoss = (float) (27 * dielectricConst / (dielectricConst - 1)
                    * (effectDielConst - 1) / Math.sqrt(effectDielConst)
                    * lossTanget / waveLenghtFreeSpace);
        }
        return dielectricLoss;
    }

    public float getConductLoss(float conduct, float freq, float waveResist, float conductWidth) {
        float conductLoss = 0;
        float surfaceResist;
        float deltaC;
        if (conduct != 0 && freq != 0 && waveResist != 0 && conductWidth != 0) {
            deltaC = (float) Math.sqrt(2 / (MAGNETIC_CONST * freq * conduct));
            surfaceResist = 1 / (conduct * deltaC);
            conductLoss = (float) ((8.68 * surfaceResist) / (waveResist * conductWidth));
        }
        return conductLoss;
    }


    public float getTotalAttenuation(float dielectricLoss, float conductLoss) {
        float totalAttenuation = 0;
        if (dielectricLoss != 0 && conductLoss != 0) {
            totalAttenuation = dielectricLoss + conductLoss;
        }
        return totalAttenuation;
    }

}
