package com.rmn.gdxtend.gl.facets;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.GL20;

public class GLStateTest {

	@Mock
	public GL20 context;

	public GLStateTest() {
		super();
	}

	@Before
	public void before() {
		MockitoAnnotations.initMocks( this );
	}

}
