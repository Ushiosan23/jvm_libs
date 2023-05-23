package utilities

import org.gradle.api.publish.Publication
import org.gradle.plugins.signing.SigningExtension
import types.SigningInfo

fun signPublications(ext: SigningExtension, config: SigningInfo, vararg publications: Publication): Unit =
	with(ext) {
		// Sign elements
		useInMemoryPgpKeys(
			config.keyId,
			config.pgpKeyB64,
			config.password)
		
		// Sign publications
		sign(*publications)
	}