package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.exception.FileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Objects;

import static fr.ubx.poo.ubomb.game.EntityCode.*;

public class GridRepoFile extends GridRepo {
    public GridRepoFile(Game game) {
        super(game);
    }

    @Override
    public final Grid load(int level, String name) throws Exception {
        FileReader fileReader = new FileReader(name + ".txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder gameInChar = new StringBuilder();
        LinkedList<Integer> widthArray = new LinkedList<>();

        for (Object line : bufferedReader.lines().toArray()) {
            gameInChar.append(line);
            widthArray.add(line.toString().length());
        }

        int width, height;

        if (widthArray.stream().allMatch(suppWidth -> Objects.equals(widthArray.get(0), suppWidth))) {
            width = widthArray.get(0);
            height = widthArray.size();

            if (!(width * height == gameInChar.length())) {
                throw new FileException("The file does not respect the writing conventions.");
            }
        } else {
            throw new FileException("The file does not respect the writing conventions.");
        }

        Grid newGrid = new Grid(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position position = new Position(level, x, y);
                newGrid.set(position, processEntityCode(fromCode(gameInChar.charAt(y * width + x)), position));
            }
        }

        int countNextClosed = (int) newGrid.values().stream().filter(object -> object.isEntity(DoorNextClosed)).count();
        int countPrevOpened = (int) newGrid.values().stream().filter(object -> object.isEntity(DoorPrevOpened)).count();

        if((countNextClosed != 0 && level == game.getGameConfig().getLevels()) || (countNextClosed != 1 && level != game.getGameConfig().getLevels())) {
            throw new FileException("Bad file construction on level " + level);
        }

        if((countPrevOpened != 0 && level == 1) || (countPrevOpened != 1 && level != 1)) {
            throw new FileException("Bad file construction on level " + level);
        }

        return newGrid;
    }
}