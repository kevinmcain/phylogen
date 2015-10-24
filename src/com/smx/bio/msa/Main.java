/**
 * 
 */
package com.smx.bio.msa;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.template.Profile;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;


/**
 * @author kevin
 *
 */
public class Main {

	public static void main(String[] args) {
		
		String[] fastaFileNames = new String[] {
				"C:\\genomes\\tenericutes\\italianclover.fna",
				"C:\\genomes\\tenericutes\\phoenicium.fna"};
		
		Map<String, DNASequence> linkedHashMap = null;
		
		try
		{
			linkedHashMap = getLinkedHashMapOfSeq(fastaFileNames);	
		}
		catch(Exception e)
		{
			
		}
        
		List<DNASequence> list = new ArrayList<DNASequence>(linkedHashMap.values());
		
		Profile<DNASequence, NucleotideCompound> profile = 
				Alignments.getMultipleSequenceAlignment(list);
		
        //Profile<ProteinSequence, AminoAcidCompound> 
        	//profile = Alignments.getMultipleSequenceAlignment(lst);
        
        System.out.printf("Clustalw:%n%s%n", profile);
        
        //ConcurrencyTools.shutdown();
	} 
	
	// http://www.programcreek.com/java-api-examples/index.php?api=org.biojava3.core.sequence.DNASequence
	// http://biojava.org/wiki/BioJava:CookBook3:MSA
	private static Map<String, DNASequence> getLinkedHashMapOfSeq(String[] fastaFileNames) throws Exception {
        //URL uniprotFasta = new URL(String.format("http://www.uniprot.org/uniprot/%s.fasta", uniProtId));
		
		Map<String, DNASequence> linkedHashMap = new LinkedHashMap<String, DNASequence>(); 
		
		for(String fastaFileName : fastaFileNames){
			InputStream inputStream = new FileInputStream(fastaFileName);
			Map<String, DNASequence> tmplinkedHashMap = FastaReaderHelper.readFastaDNASequence(inputStream);
			linkedHashMap.putAll(tmplinkedHashMap);
		}

        return linkedHashMap;
    }
}
