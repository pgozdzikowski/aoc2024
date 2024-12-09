package pl.gozdzikowski.pawel.adventofcode.day9;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiskFragmenter {

    public long calculateChecksumMovingPartOfFile(Input input) {
        int[] diskMap = input.get().stream().flatMap((el) -> Arrays.stream(el.split("")).map(Integer::parseInt))
                .mapToInt(Integer::intValue)
                .toArray();

        Disk disk = new Disk(diskMap);

        return disk.compressMovingPartOfFile()
                .calcChecksum();
    }

    public long calculateChecksumOfCompressedFileMovingWholeFile(Input input) {
        int[] diskMap = input.get().stream().flatMap((el) -> Arrays.stream(el.split("")).map(Integer::parseInt))
                .mapToInt(Integer::intValue)
                .toArray();

        Disk disk  = new Disk(diskMap);
        return disk.compressMovingWholeFile()
                .calcChecksum();
    }


    static class Disk {

        private final int[] disk;

        public Disk(int[] diskMap) {
            this.disk = createDisk(diskMap);
        }

        public Disk compressMovingWholeFile() {
            int indexOfLastFile = findLastFile(disk, disk.length - 1);
            while (indexOfLastFile >= 0) {
                int currentFileSize = calculateLengthOfFile(disk, indexOfLastFile);
                indexOfLastFile -= (currentFileSize - 1);
                int indexOfGap = findGapOfLength(indexOfLastFile, currentFileSize, disk);
                if (indexOfGap != -1) {
                    for (int i = 0; i < currentFileSize; i++) {
                        disk[indexOfGap + i] = disk[indexOfLastFile + i];
                        disk[indexOfLastFile + i] = -1;
                    }
                }
                indexOfLastFile = findLastFile(disk, indexOfLastFile - 1);
            }
            return this;
        }


        public Disk compressMovingPartOfFile() {
            int indexOfLastFile = findLastFile(disk, disk.length - 1);
            for (int i = 0; i < indexOfLastFile; i++) {
                if (disk[i] == -1) {
                    disk[i] = disk[indexOfLastFile];
                    disk[indexOfLastFile] = -1;
                    indexOfLastFile = findLastFile(disk, indexOfLastFile - 1);
                }
            }

            return this;
        }

        private int[] createDisk(int[] diskMap) {
            List<Integer> currentDisk = new ArrayList<>();
            int currentFileIndex = 0;
            for (int i = 0; i < diskMap.length; i++) {
                if (i % 2 == 0) {
                    for (int j = 0; j < diskMap[i]; j++) {
                        currentDisk.add(currentFileIndex);
                    }
                } else {
                    currentFileIndex++;
                    for (int j = 0; j < diskMap[i]; j++) {
                        currentDisk.add(-1);
                    }
                }
            }

            return currentDisk.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
        }

        public long calcChecksum() {
            long result = 0;

            for (int i = 0; i < disk.length; ++i) {
                if (disk[i] != -1) {
                    result += (long) i * disk[i];
                }
            }

            return result;
        }

        private int calculateLengthOfFile(int[] disk, int startingIndex) {
            int currentPosition = startingIndex;
            int length = 0;
            int fileIndex = disk[startingIndex];
            while (currentPosition >= 0 && disk[currentPosition] == fileIndex && (disk[currentPosition] != -1)) {
                length++;
                currentPosition--;
            }
            return length;
        }

        private int findGapOfLength(int indexOfFileStart, int fileSize, int[] disk) {
            int currentGapSize = 0;
            for(int i = 0; i < disk.length && i <= indexOfFileStart; i++) {
                if(currentGapSize >= fileSize)
                    return i - fileSize;

                if(disk[i] == -1) {
                    currentGapSize++;
                } else {
                    currentGapSize = 0;
                }
            }

            return -1;
        }

        private int findLastFile(int[] disk, int position) {
            int currentPosition = position;

            while (currentPosition >= 0) {
                if (disk[currentPosition] >= 0) {
                    return currentPosition;
                }
                currentPosition--;
            }

            return -1;
        }
    }
}
