package br.com.milano.controle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
	
import br.com.milano.modelo.Filtro;
import br.com.milano.persistence.dao.PisCofinsExcel;
import br.com.milano.persistence.dao.PisConfisPdf;

@ManagedBean(name="filtroMB") 
@RequestScoped
public class Controle {
	
	private Filtro filtro;//Clase modelo do formul�rio.
	
	//Contrutor
	public Controle() {
		this.filtro = new Filtro();
	}
	

	public void gerarPdf(){
		try{
			//Testa se as datas est�o sendo preenchidas de forma correta
			if(filtro.getDataInicial() == null || filtro.getDataFinal() == null)
			{
            	 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Aten��o!",  "Campos data inicial e final s�o obrigat�rios!")); 
            }
			//Testa se a data inicial est� maior que a data final
	  		else if(! filtro.getDataFinal().after(filtro.getDataInicial()) && !filtro.getDataFinal().equals(filtro.getDataInicial()))
	  		{
            	 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Aten��o!",  "Campos data inicial n�o pode ser maior que data final, por favor tente novamente!"));	 
            }
			//Testa se a op��o n�o est� vazia
	  		else if(filtro.getOpcao() == 0 ){
	  			 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Aten��o!",  "Campo op��o obrigat�rio! Por favor, selecione uma op��o para gerar relat�rio por compet�ncia ou regime de caixa!"));	 
	  		}
	  		else//Verifica qual a op��o selecionada para gerar o relat�rio correspondente
	  			if(filtro.getOpcao() == 1 ){
	            	new PisConfisPdf().pdfCompetencia(filtro);
	            }else{
	            	new PisConfisPdf().pdfRegimeCaixa(filtro);
	    		}
			
		}catch(Exception e){
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	public void gerarExcel(){
		try{
			//Testa se as datas est�o sendo preenchidas de forma correta
			if(filtro.getDataInicial() == null || filtro.getDataFinal() == null)
			{
            	 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Aten��o!",  "Campos data inicial e final s�o obrigat�rios!")); 
            }
			//Testa se a data inicial est� maior que a data final
	  		else if(! filtro.getDataFinal().after(filtro.getDataInicial()) && !filtro.getDataFinal().equals(filtro.getDataInicial()))
	  		{
            	 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Aten��o!",  "Campos data inicial n�o pode ser maior que data final, por favor tente novamente!"));	 
            }
			//Testa se a op��o n�o est� vazia
	  		else if(filtro.getOpcao() == 0 ){
	  			 FacesContext context = FacesContext.getCurrentInstance();
                 context.addMessage(null, new FacesMessage("Aten��o!",  "Campo op��o obrigat�rio! Por favor, selecione uma op��o para gerar relat�rio por compet�ncia ou regime de caixa!"));	 
	  		}
			//Verifica qual a op��o selecionada para gerar o relat�rio correspondente!
	  		else if(filtro.getOpcao() == 1 )
	  		{
            	new PisCofinsExcel().gerarPisCofinsExcel(filtro);//Relat�rio excel pis cofins.
            }
	  		else
	  		{
            	new PisCofinsExcel().excelPorRegimeCaixa(filtro);//Relat�rio Por regime de caixa.
    		}//Fim do bloco de verifica��o
			
		}catch(Exception e){
			
		}//Fim do try/catch
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}
}
