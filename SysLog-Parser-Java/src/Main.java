// V2 - Utilizando um HashMap

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // 0.1 Apenas para comparar desempenho
        long inicio = System.nanoTime();

        // 1. Verifica se o caminho do arquivo.txt foi passado como argumento
        if (args.length == 0) {
            System.out.println("Argumento de caminho vazio. Passe o caminho de um arquivo para ser lido pelo programa");
            return;
        }

        String caminhoDoArquivo = args[0];
        Map<String, Processo> mapaDeProcessos = new HashMap<>();

        // 2. Acessa o arquivo.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoDoArquivo))) {
            String linha;

            // 3. Lê o arquivo linha por linha
            while ((linha = reader.readLine()) != null) {

                // 4. Separa a linha em colunas
                String[] colunas = linha.split("\\|");

                // 5. Retira os espaços em branco das colunas
                for(int i = 0; i < colunas.length; i++){
                    colunas[i] = colunas[i].trim();
                }

                // 6. Verifica a integridade da linha e ignora caso não seja valida
                if(!verificaIntegridadeLinha(colunas)){
                    System.err.println("Linha Inválida Ignorada: " + linha);
                    continue;
                }

                long timestamp = Long.parseLong(colunas[0]);
                int uid = Integer.parseInt(colunas[1]);
                int pid = Integer.parseInt(colunas[2]);
                int eventType = Integer.parseInt(colunas[3]);
                String processName = colunas[4];

                // 7. Procura o processo no mapa de processos
                Processo processoAlvo = mapaDeProcessos.get(processName);

                //7.1 Caso não encontre, insere o processo no mapa
                if(processoAlvo == null){
                    processoAlvo = new Processo(processName, uid, pid);
                    mapaDeProcessos.put(processName, processoAlvo);
                }

                // 7.2 Processa o evento
                processoAlvo.processarEvento(eventType, timestamp);

            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        for(Processo processo : mapaDeProcessos.values()){

            // 8 - Imprime o resultado formatado no terminal para debug
            System.out.println("Processo: " + processo.getProcessName());
            System.out.println(processo.toString());

            // 9 - Imprime o resultado nos arquivos de destino da pasta output
            String pastaDeSaida = "output/";
            String nomeArquivoSanitizado = sanitizarNomeArquivo(processo.getProcessName()) + ".txt";

            try {
                Path diretorio = Paths.get(pastaDeSaida);
                Files.createDirectories(diretorio);

                Path caminhoCompleto = diretorio.resolve(nomeArquivoSanitizado);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoCompleto.toFile()))) {
                    writer.write(processo.toString());
                }
            }
            catch (IOException e) {
                System.err.println("Erro ao escrever o arquivo para o processo " + processo.getProcessName() + ": " + e.getMessage());
            }
        }

        // 0.2 Apenas para comparar desempenho
        long fim = System.nanoTime();
        long duracaoEmNanos = fim - inicio;

        double duracaoEmMillis = duracaoEmNanos / 1_000_000.0;
        System.out.println("Tempo de processamento: " + duracaoEmMillis + " ms");

    }

    public static boolean verificaIntegridadeLinha(String[] colunas) {
        // 1º caso -> A linha possui um número de colunas diferente de 5
        if (colunas.length != 5) {
            return false;
        }

        // 2º caso -> Uma coluna está vazia ou sem dados
        for(String coluna : colunas){
            if(coluna.isBlank()){
                return false;
            }
        }

        // 3º caso -> As colunas numéricas não sejam números
        try {
            Long.parseLong(colunas[0]);
            Integer.parseInt(colunas[1]);
            Integer.parseInt(colunas[2]);
            Integer.parseInt(colunas[3]);
        } catch (NumberFormatException e) {
            // Se uma das conversões falhar a linha é inválida
            return false;
        }

        return true;
    }

    public static String sanitizarNomeArquivo(String nome) {
        // Tudo que não for um carácter, número, ponto ou - é trocado por underline
        return nome.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

}