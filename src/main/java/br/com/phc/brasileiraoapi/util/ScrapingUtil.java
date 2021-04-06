package br.com.phc.brasileiraoapi.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.phc.brasileirao.dto.PartidaGoogleDTO;

public class ScrapingUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScrapingUtil.class);
	private static final String BASE_URL_GOOGLE = "https://www.google.com.br/search?q=";
	private static final String COMPLEMENTO_URL_GOOGLE = "&HL=PT-br";
	
	private static final String BASE_URL_SOFASCORE = "https://www.sofascore.com/wellington-phoenix-melbourne-city/tbdskUq";
	private static final String COMPLEMENTO_URL_SOFASCORE = "";
	
	public static void main(String[] args) {
		String url = BASE_URL_GOOGLE + "Spartak+Trnava+-+AS+Trenčín" + COMPLEMENTO_URL_GOOGLE;
		//String url = BASE_URL_SOFASCORE;
		
		ScrapingUtil scraping = new ScrapingUtil();
		scraping.obtemInformacoesPartida(url);

	}
	
	public PartidaGoogleDTO obtemInformacoesPartida(String url) {
		PartidaGoogleDTO partida = new PartidaGoogleDTO();		
		Document document = null;
		try {
			document = Jsoup.connect(url).get(); // conexao com a página	
			String title = document.title();
			LOGGER.info("TITULO DA PAGINA: {} "+title);
			StatusPartida statusPartida = obtemStatusPartida(document);
			ObtemTempoPartida(document);
			
		} catch (IOException e) {
			LOGGER.error("Erro Ao Conectar no Google com Jsoup - {}",e.getMessage());
			e.printStackTrace();
		}
				
		return partida;
	}

	public StatusPartida obtemStatusPartida(Document document) {
		StatusPartida statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;
		
		boolean isTempoPartida = document.select("div[class=imso_mh__lv-m-stts-cont]").isEmpty();
		if (!isTempoPartida) {
			//busca o primeiro elemento e usa o text para trazer o que tá dentro
			String tempoPartida = document.select("div[class=imso_mh__lv-m-stts-cont]").first().text();
			statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;	
			LOGGER.info(statusPartida.toString());
		}
		
		isTempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").isEmpty();
		if (!isTempoPartida) {
			//busca o primeiro elemento e usa o text para trazer o que tá dentro
			String tempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").first().text();
			statusPartida = StatusPartida.PARTIDA_ENCERRADA;	
			LOGGER.info(statusPartida.toString());
		}			
		return statusPartida;
	}
	
	public String ObtemTempoPartida(Document document) {
		String tempoPartida = null;
		boolean isTempoPartida = document.select("span[class=liveresults-sports-immersive__game-minute]").isEmpty();
		if (!isTempoPartida) {
			tempoPartida = document.select("span[class=liveresults-sports-immersive__game-minute]").first().text();
			LOGGER.info(corrigeTempoPartida(tempoPartida));
		}
		return corrigeTempoPartida(tempoPartida);
	}
	
	public String corrigeTempoPartida(String tempo) {
		if (tempo.contains("'")) {
			return tempo.replace("'", "");
		} else if (tempo.contains("+")){
			return tempo.replace(" ", "");
		} else {
			return tempo;
		}
	}
	
}












