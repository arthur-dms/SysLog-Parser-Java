import java.util.*;

public class Processo {
    private final String processName;
    private final int uid;
    private final int pid;
    private Map<Integer, Evento> tiposEvento;

    Processo(String processName, int uid, int pid){
        this.processName = processName;
        this.uid = uid;
        this.pid = pid;
        this.tiposEvento = new HashMap<>();
    }

    public String getProcessName(){
        return this.processName;
    }

    public List<Evento> getTiposEventoOrdenados(){
        List<Evento> eventos = new ArrayList<>(this.tiposEvento.values());
        eventos.sort(null);
        return eventos;
    }

    public void processarEvento(int eventType, long timestamp) {
        Evento evento = tiposEvento.get(eventType);

        // Caso o Evento não exista, cria e insere
        if (evento == null) {
            evento = new Evento(eventType, timestamp);
            tiposEvento.put(eventType, evento);
        }

        // Caso o Evento já exista, apenas atualiza
        else {
            evento.incrementaOcorrencia();
            evento.verificaNovoTimestamp(timestamp);
        }
    }

    // Ao modificar o equals() é obrigatório modificar o hashCode() tbm
    @Override
    public boolean equals(Object objeto){
        if(this == objeto){
            return true;
        }
        if(this.getClass() != objeto.getClass()){
            return false;
        }

        Processo outroProcesso = (Processo) objeto;

        return this.processName.equals(outroProcesso.getProcessName());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.processName);
    }

    @Override
    public String toString(){
        String formatoCabecalho = "EVENT| UID| PID| PROCESS_NAME | COUNTER | FIRST TIMESTAMP\n";
        StringBuilder resultado = new StringBuilder(formatoCabecalho);
        String formatoLinha = "%5d|%4d|%4d|%13s |%8d | %d\n";

        for(Evento evento : this.getTiposEventoOrdenados()){
            resultado.append(String.format(formatoLinha, evento.getTipoEvento(), this.uid, this.pid, this.processName, evento.getOcorrencias(), evento.getTimestampPrimeiraOcorrencia()));
        }

        return resultado.toString();
    }

}