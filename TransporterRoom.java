public class TransporterRoom extends Room
{
    /**
     * Return a random room, independent of the direction
     * parameter.
     * @param direction Ignored.
     * @return A random room.
     */
    public Room getExit(String direction)
    {
        return findRandomRoom();
    }
    /**
     * Choose a random room.
     * @return A random room.
     */
    private Room findRandomRoom()
    {
         // implementation omitted
    }
}