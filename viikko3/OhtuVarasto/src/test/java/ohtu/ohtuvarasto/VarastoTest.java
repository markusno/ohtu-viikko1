package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void uusiVarastoEiHyvaksyNegatiivistaSaldoa() {
        varasto = new Varasto(10, -5);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void uusiVarastoEiHyvaksyNegatiivistaTilavuutta() {
        varasto = new Varasto(-1);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        varasto = new Varasto(-1, 3);
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void uusiIsoVarasto() {
        varasto = new Varasto(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, varasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void uusiIsoVarastoTaynna() {
        varasto = new Varasto(Double.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, varasto.getTilavuus(), vertailuTarkkuus);
        assertEquals(Double.MAX_VALUE, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void uusiVarastoRajaTapaukset() {
        varasto = new Varasto(-vertailuTarkkuus);
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
        varasto = new Varasto(vertailuTarkkuus);
        assertEquals(vertailuTarkkuus, varasto.getTilavuus(), vertailuTarkkuus);
        varasto = new Varasto(vertailuTarkkuus, -vertailuTarkkuus);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        varasto = new Varasto(vertailuTarkkuus, vertailuTarkkuus);
        assertEquals(vertailuTarkkuus, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void uusiVarastoEiHyvaksyTilavuuttaSuurempaaSaldoa() {
        varasto = new Varasto(1, 3);
        assertEquals(1, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void konstr() {
        varasto = new Varasto(-1);
        varasto = new Varasto(0);
        varasto = new Varasto(1,1);
        varasto = new Varasto(1,2);
        varasto = new Varasto(-1,2);
        varasto = new Varasto(-1,-1);
        assertNotNull(varasto.toString());
    }
    
    @Test
    public void lisaysNegatiivinenEiMuutaSaldoa() {
        varasto.lisaaVarastoon(5);
        double saldo = varasto.getSaldo();
        varasto.lisaaVarastoon(-3);
        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(saldo, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void lisaysYlivuotoTilanloppuessa() {
        varasto.lisaaVarastoon(12);
        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenNegatiivinenEiMuutaSaldoa() {
        varasto.lisaaVarastoon(5);
        double saldo = varasto.getSaldo();
        varasto.otaVarastosta(-5);
        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(saldo, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenNegatiivinenPalauttaaNollan() {
        varasto.lisaaVarastoon(5);
        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(0, varasto.otaVarastosta(-5) , vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenSaldoaSuurempiPalauttaaKokoSaldon() {
        varasto.lisaaVarastoon(8);
        double saldo = varasto.getSaldo();
        double maara = varasto.otaVarastosta(saldo+3);
        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(saldo, maara, vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenSaldoaSuurempiTyhjentaa() {
        varasto.lisaaVarastoon(8);
        double saldo = varasto.getSaldo();
        double maara = varasto.otaVarastosta(saldo+3);
        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
}