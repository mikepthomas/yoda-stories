/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.zone.action;

import com.zachtronics.yodastories.parser.BinaryReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike
 */
public class ActionReader extends BinaryReader {

    public ActionReader(InputStream in) {
        super(in);
    }

    public Action readObject() throws IOException {
        Action action = new Action();

        available();

        ActionType actionType= ActionType.parse(readUInt16());
        DataType dataType = DataType.parse(readUInt16());

        int x = readUInt16();
        int y = readUInt16();
        int tile = readUInt16();
        System.out.println("location = " + x + "x" + y);
        System.out.println("tile = " + tile);

        if (actionType != ActionType.AREA_PORTAL) {
            while (available() != 0) {

                String header = readChars(6);

                switch (header) {
                    case "":
                    case "40F":
                    case "16,6":
                    case "AddToC":
                    case "AddToH": // Add to health
                    case "AutoCl":
                    case "Back":
                    case "Bump": // Bump into something
                    case "Coun":
                    case "Ending":
                    case "EnemyO":
                    case "Ente":
                    case "EveryE":
                    case "Find":
                    case "Firs":
                    case "Flip":
                    case "GamesW":
                    case "Glob":
                    case "Global":
                    case "GOIt":
                    case "GOItem":
                    case "Hero":
                    case "HotSpo":
                    case "Move":
                    case "MoveTi":
                    case "MS S":
                    case "MS San":
                    case "NoWay-":
                    case "oneJ": // red herring (starts lower case)?
                    case "Plac":
                    case "Play":
                    case "Rand":
                    case "RandNu":
                    case "Read":
                    case "ReadTF":
                    case "Ready":
                    case "Redraw":
                    case "Remove":
                    case "Replac":
                    case "Requ":
                    case "Scor P":
                    case "SetC":
                    case "SetCou":
                    case "Set-D4":
                    case "SetG":
                    case "SetGlo":
                    case "SetH":
                    case "SetHer":
                    case "SetM":
                    case "SetRan":
                    case "Setu":
                    case "setu":
                    case "Show":
                    case "ShowHe":
                    case "TalkHa":
                    case "TEST":
                    case "ThrowR":
                    case "Tile":
                    case "TileAt":
                    case "up L": // red herring (starts lower case)?
                    case "up Lev": // red herring (starts lower case)?
                    case "UseAno":
                    case "UseIte":
                    case "WaitFo":
                    case "Walk":
                    case "XDn":
                    case "Zone":
                        byte[] data = readBytes(8);
                        break;

                    case "BumpTi": // Bump Tile
                        int bumpTextLength = readUInt16();
                        String bumpText = readChars(bumpTextLength);
                        System.out.println("Bump Text: " + bumpText);
                        break;

                    case "Counte":
                        int counteTextLength = readUInt16();
                        String counteText = readChars(counteTextLength);
                        System.out.println("Counte Text: " + counteText);
                        break;

                    case "DrawTi": // draw tile
                        byte[] drawTiData = readBytes(22);
                        break;

                    case "Endi":
                        byte[] endiData = readBytes(22);
                        break;

                    case "HasI":
                    case "HasIte": // has item
                        int hasIteTextLength = readUInt16();
                        String hasIteText = readChars(hasIteTextLength);
                        System.out.println("HasIte Text: " + hasIteText);
                        byte[] hasIData = readBytes(6);
                        break;

                    case "PlaySo": // Play Sound
                        int zero1 = readUInt16();
                        int soundNumber = readUInt16();
                        int numOfTimes = readUInt16();
                        int zero2 = readUInt16();
                        break;

                    case "ShowTe": // Show Text
                        int showTextLength = readUInt16();
                        String showText = readChars(showTextLength);
                        System.out.println("Show Text: " + showText);
                        byte[] showTeData = readBytes(6);
                        break;

                    case "View":
                        byte[] viewData = readBytes(28);
                        int viewTextLength = readUInt16();
                        String viewText = readChars(viewTextLength);
                        System.out.println("View: " + viewText);
                        available();
                        break;

                    case "Wait":
                        int waitLength = readUInt16();
                        byte[] waitData = readBytes(waitLength);
                        break;

                    case "Sarlac":
                        byte[] sarlacData = readBytes(22);
                        break;

                    case "Zone T":
                        int zoneTextLength = readUInt16();
                        String zoneText = readChars(zoneTextLength);
                        System.out.println("Zone Text: " + zoneText);
                        break;

                    case "ZX:0":
                    case "ZX:1":
                    case "ZX:00 ":
                    case "ZX:01 ":
                    case "ZX:02 ":
                    case "ZX:03 ":
                    case "ZX:06 ":
                    case "ZX:07 ":
                    case "ZX:08 ":
                    case "ZX:10 ":
                        int zx0TextLength = readUInt16();
                        String zx0Text = readChars(zx0TextLength);
                        System.out.println("ZX:0 Text: " + zx0Text);
                        break;

                    default:
                        byte[] unknown = readBytes(8);
                        System.out.println("unknown header: " + header);
                        break;

                }
            }
        }

        return action;
    }
}
