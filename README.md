# LogParser-Java

Este projeto é uma ferramenta de linha de comando desenvolvida em **Java** para processar arquivos de logs de eventos de sistema. O desafio original, proposto em C++, foi adaptado para Java para demonstrar habilidades em Manipulação de Arquivos (I/O), Coleções (Collections) e Orientação a Objetos.

## O Desafio

O programa recebe um arquivo de texto via argumento de terminal com logs estruturados. O objetivo é realizar o parsing desses dados, agrupar as informações por processo e gerar relatórios individuais formatados.

**Requisitos atendidos:**
* **Parsing Dinâmico:** Leitura e interpretação de logs no formato `TIMESTAMP | EVENT | UID | PID | PROCESS_NAME`.
* **Segregação de Arquivos:** Geração de um arquivo `.txt` individual para cada processo encontrado (ex: `youtube.txt`, `chrome.txt`, `tiktok.txt`).
* **Agrupamento e Contagem:** Identificação de eventos únicos por processo e contagem total de suas ocorrências.
* **Ordenação:** Garantia de que o conteúdo dos arquivos de saída esteja ordenado por tipos de eventos.
* **Transformação de Dados:** O timestamp da primeira ocorrência de cada evento é extraído e movido para a última coluna do arquivo gerado.

## Exemplo de Fluxo

### Entrada (`logs.txt`):
```text
TIMESTAMP  | EVENT | UID   | PID | PROCESS_NAM'E
1090019010 | 122   | 10300 | 879 | tiktok
1090019030 | 332   | 10331 | 97  | youtube
1090059034 | 332   | 10331 | 97  | youtube
1090079022 | 265   | 10331 | 97  | youtube
```

### Saida(`youtube.txt`):
```text
EVENT | UID   | PID | PROCESS_NAME | COUNTER | FIRST TIMESTAMP
265   | 10331 | 97  | youtube      | 1       | 1090079022
332   | 10331 | 97  | youtube      | 2       | 1090019030
```

### Saida(`tiktok.txt`):
```text
EVENT | UID   | PID | PROCESS_NAME | COUNTER | FIRST TIMESTAMP
122   | 10300 | 879  | youtube      | 1       | 1090019010
```
## Como Executar
### Clone o repositório
```bash
git clone 
```

### Compile o projeto
```bash
javac Main.java
```

### Execute passando o arquivo de log como argumento
```bash
java Main input/log_1k_linhas.txt
```