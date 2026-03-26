import java.util.Comparator;
import java.util.Objects;

public class Evento implements Comparable<Evento> {
    private final int tipoEvento;
    private long timestampPrimeiraOcorrencia;
    private int ocorrencias;

    Evento(int tipoEvento, long timestampPrimeiraOcorrencia){
        this.tipoEvento = tipoEvento;
        this.timestampPrimeiraOcorrencia = timestampPrimeiraOcorrencia;
        this.ocorrencias = 1;
    }

    public int incrementaOcorrencia(){
        this.ocorrencias += 1;
        return this.ocorrencias;
    }

    public void verificaNovoTimestamp(Long outroTimestamp){
        if(this.timestampPrimeiraOcorrencia > outroTimestamp){
            this.timestampPrimeiraOcorrencia = outroTimestamp;
        }
    }

    public int getTipoEvento(){
        return this.tipoEvento;
    }

    public int getOcorrencias() {
        return ocorrencias;
    }

    public long getTimestampPrimeiraOcorrencia(){
        return this.timestampPrimeiraOcorrencia;
    }

    @Override
    public boolean equals(Object objeto){
        if(this == objeto){
            return true;
        }

        if(objeto == null || objeto.getClass() != this.getClass()){
            return false;
        }

        Evento outroEvento = (Evento) objeto;
        return this.tipoEvento == outroEvento.getTipoEvento();
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoEvento);
    }

    @Override
    public int compareTo(Evento outroEvento){
        return Integer.compare(this.tipoEvento, outroEvento.getTipoEvento());
    }

    @Override
    public String toString() {
        return String.format("\tEvento[Tipo=%d, COUNTER=%d, ]", tipoEvento, ocorrencias);
    }

}
