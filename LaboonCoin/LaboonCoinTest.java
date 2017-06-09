import static org.junit.Assert.*;

import org.junit.*;

public class LaboonCoinTest {

    // Assert that creating a new LaboonCoin instance
    // does not return a null reference
    @Test
    public void testLaboonCoinExists() {
	LaboonCoin l = new LaboonCoin();
	assertNotNull(l);
    }
    
    //assert that data returned is valid based on input
    @Test
    public void createBlockValid() {
        LaboonCoin l = new LaboonCoin();
        String data = "Bill Gave Joe $10";
        int prevHash = 0;
        int nonce = 0x1aa59c;
        int hash = 0xe854a;
        String expected = "Bill Gave Joe $10|00000000|001aa59c|000e854a";
        
        String observed = l.createBlock(data, prevHash, nonce, hash);
        assertEquals(expected, observed); 
    }
    
    //assert that data returned is valid given empty data field
    @Test
    public void createBlockValidEmptyData() {
        LaboonCoin l = new LaboonCoin();
        String data = "";
        int prevHash = 0;
        int nonce = 0x1aa59c;
        int hash = 0xe854a;
        String expected = "|00000000|001aa59c|000e854a";
        
        String observed = l.createBlock(data, prevHash, nonce, hash);
        assertEquals(expected, observed); 
    }
    
    //assert that all empty or 0 values does not equal an empty string
    @Test
    public void createBlockValiEmptyAll() {
        LaboonCoin l = new LaboonCoin();
        String data = "";
        int prevHash = 0;
        int nonce = 0;
        int hash = 0;
        
        String observed = l.createBlock(data, prevHash, nonce, hash);
        assertNotEquals(observed, ""); 
    }

    //assert valid return value when a single item exists in the blockchain
    @Test
    public void getBlockChainValidSingle()
    {
        LaboonCoin l = new LaboonCoin();
        String newBlock = l.createBlock("1", 0x1, 0x2, 0x3);
        l.blockchain.add(newBlock);
        
        String observed = l.getBlockChain();
        String expected = "" + System.lineSeparator() + "1|00000001|00000002|00000003";
        assertEquals(observed, expected);
    }
    
    //assert valid return value
    @Test
    public void getBlockChainValidMultiple()
    {
        LaboonCoin l = new LaboonCoin();
        String newBlock = l.createBlock("1", 0x1, 0x2, 0x3);
        l.blockchain.add(newBlock);
        
        newBlock = l.createBlock("2", 0x2, 0x3, 0x4);
        l.blockchain.add(newBlock);
        
        String observed = l.getBlockChain();
        String expected = "" + System.lineSeparator() + "1|00000001|00000002|00000003" + System.lineSeparator() + "2|00000002|00000003|00000004";

        assertEquals(observed, expected);
    }
    
    //assert that empty string is returned when there is nothing in block chain
    @Test
    public void getBlockChainEmpty()
    {
        LaboonCoin l = new LaboonCoin();
        String observed = l.getBlockChain();
        
        assertEquals("", observed);
    }
    
    //assert the happy path
    @Test
    public void hashvalid()
    {
        LaboonCoin l = new LaboonCoin();
        int observed = l.hash("boo");
        int expected = 1428150834;
        
        assertEquals(observed, expected);
    }
    
    //assert the case when string is empty
    @Test
    public void hashempty()
    {
        LaboonCoin l = new LaboonCoin();
        int observed = l.hash("");
        int expected = 10000000;
        
        assertEquals(expected, observed);
    }
    
    //assert the case when data is a number
    @Test
    public void hashnumber()
    {
        LaboonCoin l = new LaboonCoin();
        int observed = l.hash("123");
        int expected = 10000000 * 49 + 49;
        expected = expected * 50 + 50;
        expected = expected * 51 + 51;
        
        assertEquals(expected, observed);
    }
    
    //assert the happy path
    @Test
    public void validHashCorrect()
    {
        LaboonCoin l = new LaboonCoin();
        boolean valid = l.validHash(3, 0x000fd98a);
        assertTrue(valid);
    }
    
    //assert incorrect hash is reported invalid
    @Test
    public void validHashIncorrect()
    {
        LaboonCoin l = new LaboonCoin();
        boolean valid = l.validHash(4, 0x000fd98a);
        assertFalse(valid);
    }
    
    //assert zero difficulty
    @Test
    public void validHashZeroDifficulty()
    {
        LaboonCoin l = new LaboonCoin();
        boolean valid = l.validHash(0, 0x000fd98a);
        assertFalse(valid);
    }
    
}
