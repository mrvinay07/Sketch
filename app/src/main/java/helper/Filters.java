package helper;

public class Filters {
    public static native void BlackNWhite(long j);

    public static native void CartoonArt(long j);

    public static native void CartoonArt4K(long j);

    public static native void CartoonArtHD(long j);

    public static native void CartoonFilter(long j, long j2);

    public static native void CartoonFilter4K(long j, long j2);

    public static native void CartoonFilterHD(long j, long j2);

    public static native void ColorPencil(long j);

    public static native void ColorPencil4K(long j);

    public static native void ColorSketch(long j);

    public static native void ColorSketch4K(long j);

    public static native void ColorSketchFilter(long j, long j2);

    public static native void ColorSketchFilter4K(long j, long j2);

    public static native void ColorSketchFilterHD(long j, long j2);

    public static native void ColorSketchHD(long j);

    public static native void ColorValue(long j, double d);

    public static native void Crayon(long j);

    public static native void Crayon4K(long j);

    public static native void DarkPencilSketch(long j);

    public static native void DarkPencilSketch4K(long j);

    public static native void DarkPencilSketchHD(long j);

    public static native void Drawing(long j);

    public static native void Drawing4K(long j);

    public static native void DrawingHD(long j);

    public static native void DrawingTwo(long j);

    public static native void DrawingTwo4K(long j);

    public static native void DrawingTwoHD(long j);

    public static native void Gothic(long j, long j2);

    public static native void Gothic45HD(long j, long j2);

    public static native void HardStroke(long j);

    public static native void LightSketch(long j);

    public static native void LightSketch4K(long j);

    public static native void LightSketchHD(long j);

    public static native void LoadSketchTexture(long j);

    public static native void Pencil(long j);

    public static native void Pencil4K(long j);

    public static native void PencilDarkShade(long j);

    public static native void PencilDarkShade4K(long j);

    public static native void PencilDarkShadeHD(long j);

    public static native void PencilDarkStrokes(long j, long j2);

    public static native void PencilDarkStrokes4K(long j, long j2);

    public static native void PencilDarkStrokesHD(long j, long j2);

    public static native void PencilHD(long j);

    public static native void PencilLightStrokes(long j, long j2);

    public static native void PencilLightStrokesHD(long j, long j2);

    public static native void PencilSketch(long j);

    public static native void PencilSketch4K(long j);

    public static native void PencilSketch5K(long j);

    public static native void PencilSketchFilter(long j, long j2);

    public static native void PencilSketchHD(long j);

    public static native void Silhoute(long j, long j2);

    public static native void Silhoute45HD(long j, long j2);

    public static native void Sketch(long j);

    public static native void WaterColor(long j);

    public static native void WaterColor4K(long j);

    public static native void WaterColorHD(long j);

    public static native void WaterColorTwo(long j, long j2);

    public static native void WaterColorTwo4K(long j, long j2);

    public static native void WaterColorTwoHD(long j, long j2);

    public static native void WaterPainting(long j);

    public static void init() {
        System.loadLibrary("pencilfilters");
    }
}