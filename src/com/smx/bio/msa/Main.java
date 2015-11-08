/**
 * 
 */
package com.smx.bio.msa;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.template.AlignedSequence;
import org.biojava.nbio.alignment.template.Profile;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.template.Sequence;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.MultipleSequenceAlignment;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound; 
import org.biojava.nbio.core.util.ConcurrencyTools;
import org.biojava.nbio.phylo.TreeConstructionAlgorithm;
import org.biojava.nbio.phylo.TreeConstructor;
import org.biojava.nbio.phylo.TreeType;

/**
 * @author kevin
 *
 */
public class Main {

	public static void main(String[] args) {
		
		// http://www.ncbi.nlm.nih.gov/genome/browse/ 
		String[] fastaFileNames = new String[] { 
				"resources/genomes/aone.fna",
				"resources/genomes/atwo.fna",
				"resources/genomes/athree.fna",
				"resources/genomes/afour.fna",
				"resources/genomes/afive.fna"};
		
				/////"resources/genomes/leukemiavirus.fna", // Retroviridae AF033812.1
				//"resources/genomes/carnationetchedringvirus.fna", // Caulimoviridae X04658.1
				//"resources/genomes/hepbvirus.fna", // Hepadnaviridae X04615.1
				/////"resources/genomes/bovineleukemiavirus.fna", // Retroviridae AF033818.1
				//"resources/genomes/tobaccoveinclearing.fna", // Caulimoviridae AF190123.1
				/////"resources/genomes/fujinamisarcoma.fna"}; // Retroviridae AF033810.1
		
		Map<String, DNASequence> linkedHashMap = null;
		 
		try
		{
			linkedHashMap = getLinkedHashMapOfSeq(fastaFileNames);	
		}
		catch(Exception e)
		{
			System.out.printf(e.getMessage());
			return;
		}
        
		List<DNASequence> list = new ArrayList<DNASequence>(linkedHashMap.values());

		// Get MSA profile object
		Profile<DNASequence, NucleotideCompound> profile = 
			Alignments.getMultipleSequenceAlignment(list);

		MultipleSequenceAlignment<DNASequence, NucleotideCompound> 
			multipleSequenceAlignment= new MultipleSequenceAlignment <DNASequence, NucleotideCompound>();
		
		List<AlignedSequence<DNASequence,NucleotideCompound>> 
			alignedSequenceList = profile.getAlignedSequences();
		
		Sequence<NucleotideCompound> seq;
		DNASequence dnaSeq;
	       
		try {
			   
			for (int i = 0; i < alignedSequenceList.size(); i++) {     
				seq = alignedSequenceList.get(i);
				dnaSeq=new DNASequence(seq.getSequenceAsString(),seq.getCompoundSet());
				dnaSeq.setAccession(seq.getAccession());
				multipleSequenceAlignment.addAlignedSequence(dnaSeq);
			}   
		} 
		catch(Exception e) {
			System.out.printf(e.getMessage());
		}

		TreeConstructor<DNASequence, NucleotideCompound> 
			treeConstructor = new TreeConstructor<DNASequence, NucleotideCompound>(
					multipleSequenceAlignment, 
					TreeType.NJ, 
					TreeConstructionAlgorithm.PID, 
					new ProgessListenerStub());
		try	{
			treeConstructor.process();   
			String newick = treeConstructor.getNewickString(true, true);
			System.out.printf(newick);
		}
		catch (Exception e) {
			System.out.printf(e.getMessage());
		}
		
		ConcurrencyTools.shutdown();
       
		System.out.printf("Clustalw:%n%s%n", profile);
	} 
	
	// http://www.programcreek.com/java-api-examples/index.php?api=org.biojava3.core.sequence.DNASequence
	// http://biojava.org/wiki/BioJava:CookBook3:MSA
	private static Map<String, DNASequence> getLinkedHashMapOfSeq(String[] fastaFileNames) throws Exception {
		
		Map<String, DNASequence> linkedHashMap = new LinkedHashMap<String, DNASequence>(); 
		
		for(String fastaFileName : fastaFileNames){
			File file = new File(fastaFileName);
			Map<String, DNASequence> tmplinkedHashMap = FastaReaderHelper.readFastaDNASequence(file, true);
			linkedHashMap.putAll(tmplinkedHashMap);
		}

        return linkedHashMap;
    }
}
