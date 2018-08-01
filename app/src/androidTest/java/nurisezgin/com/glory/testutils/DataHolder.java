package nurisezgin.com.glory.testutils;

/**
 * Created by nuri on 01.08.2018
 */
public final class DataHolder<Data> {

    private Data data;

    public void offer(Data data) {
        this.data = data;
    }

    public Data take() {
        return data;
    }

}
