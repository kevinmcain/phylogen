package com.smx.bio.msa;

import org.biojava.nbio.phylo.NJTreeProgressListener;

public class ProgessListenerStub implements NJTreeProgressListener {

	@Override
	public void progress(Object njtree, String state, int percentageComplete) {
		// TODO Auto-generated method stub

	}

	@Override
	public void progress(Object njtree, String state, int currentCount,
			int totalCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void complete(Object njtree) {
		// TODO Auto-generated method stub

	}

	@Override
	public void canceled(Object njtree) {
		// TODO Auto-generated method stub

	}

}
