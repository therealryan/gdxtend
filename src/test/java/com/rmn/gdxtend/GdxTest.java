package com.rmn.gdxtend;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

@RunWith( GdxTestRunner.class )
public abstract class GdxTest {

	@Mock
	public GL20 gl;

	@Before
	public void before() {

		MockitoAnnotations.initMocks( this );
		Gdx.gl20 = gl;
		Gdx.gl = gl;
	}
}
