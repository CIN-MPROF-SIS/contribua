package br.cin.ufpe.contribua.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Utils {

    public static FacesMessage.Severity SUCESSO = FacesMessage.SEVERITY_INFO;
    public static FacesMessage.Severity ERROR = FacesMessage.SEVERITY_ERROR;
    public static FacesMessage.Severity FATAL = FacesMessage.SEVERITY_FATAL;
    public static String Path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");

    public static void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity severity) {
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem = new FacesMessage();
        mensagem.setSeverity(severity);
        mensagem.setSummary(sumario);
        mensagem.setDetail(detalhe);
        contexto.addMessage(null, mensagem);
    }

    public static String capitalizeStrings(String str) {

        int count = 0;
        StringBuilder token = new StringBuilder();
        String[] palavras = str.split(" ");
        String[] exc = new String[]{"e", "a", "o", "de", "da", "do", "das", "dos", "des",
            "em", "no", "na", "nos", "nas", "que", "com", "um", "uma",
            "uns", "se", "mas", "para", "delos"};

        for (String string : palavras) {
            for (String s : exc) {
                if (s.equalsIgnoreCase(string)) {
                    token.append(string.toLowerCase() + " ");
                    count++;
                }
            }
            if (count == 0) {
                token.append(string.substring(0, 1).toUpperCase());
                token.append(string.substring(1).toLowerCase());
                token.append(" ");
            }
            count = 0;
        }

        str = token.substring(0, token.length() - 1);
        str = token.toString().trim();

        return str;
    }

    public static String formataNome(String nome) {
        return nome.toLowerCase()
                .replace(" ", "-")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ú", "u")
                .replace("ç", "c")
                .replace("ó", "o")
                .replace("ã", "a")
                .replace("õ", "o")
                .replace("ê", "e")
                .replace("_", "");
    }

    public static String formataCPF(String cpf) {
        return cpf.substring(0, 3).concat(".").concat(cpf.substring(3, 6)).concat(".").concat(cpf.substring(6, 9)).concat("-").concat(cpf.substring(9, 11));
    }
}
