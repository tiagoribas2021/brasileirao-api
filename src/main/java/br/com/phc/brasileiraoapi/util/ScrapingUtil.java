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
		//String url = BASE_URL_GOOGLE + "palmeiras+x+corinthians+08/08/2020" + COMPLEMENTO_URL_GOOGLE;
		String url = BASE_URL_SOFASCORE;
		
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
			
		} catch (IOException e) {
			LOGGER.error("Erro Ao Conectar no Google com Jsoup - {}",e.getMessage());
			e.printStackTrace();
		}
		
		return partida;
	}

}












